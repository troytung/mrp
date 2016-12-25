package com.petfood.mrp.spring.config;

import java.text.MessageFormat;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.petfood.mrp.template.FreeMarkerTemplate;

@Configuration
@ComponentScan("com.petfood.mrp")
@EnableTransactionManagement
@EnableScheduling
public class AppConfig {

    @Bean
    public static PropertyPlaceholderConfigurer propertyPlaceholderConfigurer() {

        PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
        propertyPlaceholderConfigurer.setLocation(new ClassPathResource(getConfigName()));
        // Allow for other PropertyPlaceholderConfigurer instances.
        propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
        return propertyPlaceholderConfigurer;
    }

    // @Bean
    // public DataSource getDataSource(@Value("${database.servername}") String servername,
    // @Value("${database.name}") String name,
    // @Value("${database.user}") String user,
    // @Value("${database.password}") String password) {
    //
    // MysqlConnectionPoolDataSource ds = new MysqlConnectionPoolDataSource();
    // ds.setUser(user);
    // ds.setPassword(password);
    // ds.setServerName(servername);
    // ds.setDatabaseName(name);
    // ds.setUseUnicode(true);
    // ds.setCharacterEncoding("utf8");
    // return ds;
    // }

    @Bean(destroyMethod = "close")
    public DataSource getDataSource(@Value("${database.driver}") String driver, @Value("${database.url}") String url,
            @Value("${database.user}") String user, @Value("${database.password}") String password) {

        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(driver);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(password);
        ds.setInitialSize(10);
        // ds.setMaxActive(40);
        // ds.setRemoveAbandoned(true);
        ds.setMinEvictableIdleTimeMillis(300000);
        ds.setValidationQuery("SELECT 1");
        ds.setTestOnBorrow(true);
        ds.setTestWhileIdle(true);
        ds.setTimeBetweenEvictionRunsMillis(600000);

        return ds;
    }

    @Bean
    @Autowired
    public PlatformTransactionManager transactionManager(DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }

    // @Bean
    // @Autowired
    // public MethodInvokingJobDetailFactoryBean spidermanJob(DataSource datasource) {
    // JdbcTemplate jdbcTemplate = new JdbcTemplate(datasource);
    // MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
    // bean.setTargetObject(new SpidermanJob(jdbcTemplate));
    // bean.setTargetMethod("crawl");
    // bean.setConcurrent(false);
    // return bean;
    // }
    //
    // @Bean
    // @Autowired
    // public CronTriggerFactoryBean spidermanTrigger(@Qualifier("spidermanJob") MethodInvokingJobDetailFactoryBean jobBean,
    // @Value("${quartz.spiderman.cron}") String cronExpression) {
    //
    // CronTriggerFactoryBean ctBean = new CronTriggerFactoryBean();
    // ctBean.setJobDetail(jobBean.getObject());
    // ctBean.setCronExpression(cronExpression);
    // return ctBean;
    // }

    @Bean
    public FreeMarkerTemplate simpleTemplate() {
        return new FreeMarkerTemplate("/template/simple");
    }

    // @Bean
    // @Autowired
    // public MethodInvokingJobDetailFactoryBean mailJob(FreeMarkerTemplate template) {
    //
    // MethodInvokingJobDetailFactoryBean bean = new MethodInvokingJobDetailFactoryBean();
    // bean.setTargetObject(new MailJob(template));
    // bean.setTargetMethod("mail");
    // bean.setConcurrent(false);
    // return bean;
    // }
    //
    // @Bean
    // @Autowired
    // public CronTriggerFactoryBean mailTrigger(@Qualifier("mailJob") MethodInvokingJobDetailFactoryBean jobBean, @Value("${quartz.mail.cron}")
    // String cronExpression) {
    //
    // CronTriggerFactoryBean ctBean = new CronTriggerFactoryBean();
    // ctBean.setJobDetail(jobBean.getObject());
    // ctBean.setCronExpression(cronExpression);
    // return ctBean;
    // }

    // @Bean
    // @Autowired
    // public SchedulerFactoryBean scheduler(@Qualifier("spidermanTrigger") CronTriggerFactoryBean spiderTrigger, @Qualifier("mailTrigger")
    // CronTriggerFactoryBean mailTrigger) {
    //
    // SchedulerFactoryBean factory = new SchedulerFactoryBean();
    // factory.setTriggers(new Trigger[] {spiderTrigger.getObject(), mailTrigger.getObject()});
    // return factory;
    // }

    // @Bean
    // public SimpleThreadPoolTaskExecutor taskExecutor() {
    // SimpleThreadPoolTaskExecutor executor = new SimpleThreadPoolTaskExecutor();
    // executor.setThreadCount(5);
    // return executor;
    // }

    public static String getConfigName() {
        return MessageFormat.format("config.{0}.properties", System.getProperty("spring.profiles.active"));
    }

}
