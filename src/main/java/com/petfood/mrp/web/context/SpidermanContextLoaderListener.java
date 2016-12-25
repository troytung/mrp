
package com.petfood.mrp.web.context;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import com.petfood.mrp.util.SpringDbUtil;

public class SpidermanContextLoaderListener extends ContextLoaderListener {

    @Override
    public void contextInitialized(ServletContextEvent event) {
        
        super.contextInitialized(event);
        final ApplicationContext context = getCurrentWebApplicationContext();
        final Logger log = LoggerFactory.getLogger(SpidermanContextLoaderListener.class);
        
        SpringDbUtil.setApplicationContext(context);
        log.info("SpringDbUtil application context set: {}", context.toString());
    }
}
