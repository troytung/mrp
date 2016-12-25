package com.petfood.mrp.web.action;

import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.petfood.mrp.model.site.Site;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.reportAction")
public class ReportAction extends AbstractSearchAction {

    private Map<DateTime, Map<Site, Integer>> postDateCntMap;

    @Override
    public String execute() {
        prepareYearMonthList();
        return SUCCESS;
    }

    public String genReport() {

        DateTime[] dts = getDateTimeQuiteria();
        postDateCntMap = getQueryManager().getPostCnt(dts[0], dts[1]);
        return execute();
    }

    public Map<DateTime, Map<Site, Integer>> getPostDateCntMap() {
        return postDateCntMap;
    }

}
