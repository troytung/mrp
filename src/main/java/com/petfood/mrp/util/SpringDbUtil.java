package com.petfood.mrp.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.petfood.mrp.manager.BizManager;
import com.petfood.mrp.spring.config.AppConfig;
import com.petfood.mrp.web.manager.AccountManager;
import com.petfood.mrp.web.manager.CategoryManager;
import com.petfood.mrp.web.manager.QueryManager;

public class SpringDbUtil {

    // private static final JdbcDao jdbcDao;
//    private static SolrDao solrDao;
    private static BizManager bizManager;
    private static AccountManager accountManager;
    private static CategoryManager categoryManager;
    private static QueryManager queryManager;
//    static {
//        // System.setProperty("spring.profiles.active", "prd");
//        // ApplicationContext context = new
//        // AnnotationConfigApplicationContext(DevAppConfig.class,
//        // PrdAppConfig.class, TestAppConfig.class);
//
//        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        // jdbcDao = context.getBean(JdbcDao.class);
//        solrDao = context.getBean(SolrDao.class);
//        bizManager = context.getBean(BizManager.class);
//    }

    private SpringDbUtil() {
    }
    
    public static void initForNonWebContext() {
        
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        setApplicationContext(context);
        
    }
    
    public static void setApplicationContext(ApplicationContext context) {
//        solrDao = context.getBean(SolrDao.class);
        bizManager = context.getBean(BizManager.class);
        accountManager = context.getBean(AccountManager.class);
        categoryManager = context.getBean(CategoryManager.class);
        queryManager = context.getBean(QueryManager.class);
    }

    // public static JdbcDao getJdbcDao() {
    // return jdbcDao;
    // }

//    public static SolrDao getSolrDao() {
//        return solrDao;
//    }

    public static BizManager getBizmanager() {
        return bizManager;
    }

    public static AccountManager getAccountManager() {
        return accountManager;
    }

    public static CategoryManager getCategoryManager() {
        return categoryManager;
    }

    public static QueryManager getQueryManager() {
        return queryManager;
    }

}
