package com.petfood.mrp.web.action;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.homeAction")
public class HomeAction extends AbstractAction {

    @Override
    public String execute() {
        return SUCCESS;
    }

}
