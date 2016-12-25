package com.petfood.mrp.web.action;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.categoryAction")
public class CategoryAction extends AbstractCategoryAction {

    @Override
    public boolean isEmail() {
        return false;
    }

}
