package com.petfood.mrp.web.action;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.petfood.mrp.model.Customer;
import com.petfood.mrp.model.DailySchedule;
import com.petfood.mrp.model.Product;
import com.petfood.mrp.web.manager.CustomerManager;
import com.petfood.mrp.web.manager.DailyScheduleManager;
import com.petfood.mrp.web.manager.ProductManager;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.mrpAction")
public class MrpAction extends AbstractAction {

    public static enum MrpParam {

        MRP("排單", "點選日期可編輯排單", "mrp!modifyMrp.action?scheduleDt="),

        VIEWMRP("打料", "點選可檢視排單", "viewMrp!viewMrp.action?scheduleDt="),

        MRPPRODUCE("排單生產進度維護", "點選可維護排單生產進度", "mrpProduce!mrpProduce.action?scheduleDt="),

        MRPPACK("排單包裝進度維護", "點選可維護排單包裝進度", "mrpPack!mrpPack.action?scheduleDt=");

        MrpParam(String title, String desc, String actionUrl) {
            this.title = title;
            this.desc = desc;
            this.actionUrl = actionUrl;
        }

        private String title;
        private String desc;
        private String actionUrl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getActionUrl() {
            return actionUrl;
        }

        public void setActionUrl(String actionUrl) {
            this.actionUrl = actionUrl;
        }
    }

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private ProductManager productManager;

    @Autowired
    private DailyScheduleManager dailyScheduleManager;

    private List<Date> scheduleDtLst;
    private Date scheduleDt;
    private List<Customer> customers;
    private String cusCode;
    private List<Product> products;
    private List<DailySchedule> scheduleLst;
    private String scheduleJson;
    private MrpParam mrpParam;

    @Override
    public String execute() {
        setupMrpParam();
        List<Date> scheduleDtLst = new ArrayList<Date>(10);
        LocalDate ld = LocalDate.now();
        for (int i = 0; i < 10; i++) {
            scheduleDtLst.add(new Date(Date.from(ld.minusDays(i).atStartOfDay(ZoneId.systemDefault()).toInstant()).getTime()));
        }
        this.scheduleDtLst = scheduleDtLst;
        return SUCCESS;
    }

    public String modifyMrp() {
        scheduleJson = new Gson().toJson(dailyScheduleManager.getByScheduleDt(scheduleDt));
        customers = customerManager.getAllCustomers();
        return "modifyMrp";
    }

    public String saveMrp() {
        scheduleLst.removeAll(Collections.singleton(null));
        for (DailySchedule ds : scheduleLst) {
            ds.setSchedule_dt(scheduleDt);
            ds.setCreate_by(getLogin().getUserCode());
        }
        dailyScheduleManager.save(scheduleDt, scheduleLst);
        return execute();
    }

    public String saveMrpDetail() {
        for (DailySchedule ds : scheduleLst) {
            ds.setSchedule_dt(scheduleDt);
            ds.setModify_by(getLogin().getUserCode());
        }
        dailyScheduleManager.update(scheduleLst);
        return execute();
    }

    public String ajaxProducts() {
        products = productManager.searchProduct(cusCode, null);
        return "json";
    }

    public String viewMrp() {
        // TODO
        scheduleLst = dailyScheduleManager.getByScheduleDt(scheduleDt);
        setupMrpParam();
        return "viewMrp";
    }

    public String mrpProduce() {
        // TODO
        scheduleLst = dailyScheduleManager.getByScheduleDt(scheduleDt);
        setupMrpParam();
        return "mrpProduce";
    }

    public String mrpPack() {
        // TODO
        scheduleLst = dailyScheduleManager.getByScheduleDt(scheduleDt);
        setupMrpParam();
        return "mrpPack";
    }

    public List<Date> getScheduleDtLst() {
        return scheduleDtLst;
    }

    public Date getScheduleDt() {
        return scheduleDt;
    }

    public void setScheduleDt(Date scheduleDt) {
        this.scheduleDt = scheduleDt;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public String getCusCode() {
        return cusCode;
    }

    public void setCusCode(String cusCode) {
        this.cusCode = cusCode;
    }

    public List<Product> getProducts() {
        return products;
    }

    public List<DailySchedule> getScheduleLst() {
        return scheduleLst;
    }

    public void setScheduleLst(List<DailySchedule> scheduleLst) {
        this.scheduleLst = scheduleLst;
    }

    public String getScheduleJson() {
        return scheduleJson;
    }

    public MrpParam getMrpParam() {
        return mrpParam;
    }

    public void setupMrpParam() {
        this.mrpParam = MrpParam.valueOf(getActionName().toUpperCase());
    }

}
