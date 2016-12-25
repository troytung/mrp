package com.petfood.mrp.web.action;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.petfood.mrp.model.Customer;
import com.petfood.mrp.model.Product;
import com.petfood.mrp.web.manager.CustomerManager;
import com.petfood.mrp.web.manager.ProductManager;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.mrpAction")
public class MrpAction extends AbstractAction {

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private ProductManager productManager;

    private List<String> scheduleDtLst;
    private String scheduleDt;
    private List<Customer> customers;
    private String cusCode;
    private List<Product> products;

    @Override
    public String execute() {
        List<String> scheduleDtLst = new ArrayList<String>(10);
        LocalDate ld = LocalDate.now();
        for (int i = 0; i < 10; i++) {
            scheduleDtLst.add(ld.minusDays(i).format(DateTimeFormatter.ISO_LOCAL_DATE));
        }
        this.scheduleDtLst = scheduleDtLst;
        return SUCCESS;
    }

    public String modifyMrp() {
        // scheduleDt
        // TODO
        customers = customerManager.getAllCustomers();
        return "modifyMrp";
    }

    public String ajaxProducts() {
        products = productManager.searchProduct(cusCode, null);
        return "json";
    }

    public List<String> getScheduleDtLst() {
        return scheduleDtLst;
    }

    public String getScheduleDt() {
        return scheduleDt;
    }

    public void setScheduleDt(String scheduleDt) {
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

}
