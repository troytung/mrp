package com.petfood.mrp.template;

import java.io.StringWriter;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

//@Scope(BeanDefinition.SCOPE_SINGLETON)
//@Component("template.freeMarkerTemplate")
public class FreeMarkerTemplate {

    // private String templateFolder;
    private final Configuration config;

    public FreeMarkerTemplate(String templateClasspath) {

        config = new Configuration();
        config.setTemplateLoader(new ClassTemplateLoader(getClass(), templateClasspath));
        config.setObjectWrapper(new DefaultObjectWrapper());
        config.setDateFormat("yyyy/MM/dd");
        config.setDateTimeFormat("yyyy/MM/dd HH:mm:ss");
    }

    public String format(String template, Object dataObject) {

        String s = null;
        try {
            Template temp = config.getTemplate(template + ".ftl", "UTF-8");
            StringWriter sw = new StringWriter();
            temp.process(dataObject, sw);
            s = sw.toString();
            sw.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return s;
    }

}
