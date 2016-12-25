
package com.petfood.mrp.web.action;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.petfood.mrp.model.Category;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.emailCategoryAction")
public class EmailCategoryAction extends AbstractCategoryAction {

    private List<Category> nonEmailCategories;
    
    @Override
    public String execute() {
        
        nonEmailCategories = getCategoryManager().findByIsEmail(false);
        
        return super.execute();
    }
    
    @Override
    public String save() {
        
        if(getCategory().getId() == null) {
            if(getCategoryManager().countCategories(true) == 5) {
                setErrorMsg("Email Category最多只能有5個，請先刪除一個再新增");
                return execute();
            }
        }
        
        return super.save();
    }
    
    public List<Category> getNonEmailCategories() {
        return nonEmailCategories;
    }

    @Override
    public boolean isEmail() {
        return true;
    }

}
