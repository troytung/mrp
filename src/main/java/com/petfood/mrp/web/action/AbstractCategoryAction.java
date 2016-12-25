
package com.petfood.mrp.web.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.petfood.mrp.model.Category;
import com.petfood.mrp.web.manager.CategoryManager;

public abstract class AbstractCategoryAction extends AbstractAction {

    private List<Category> categories;
    private Category category;
    private String errorMsg;

    @Autowired
    private CategoryManager categoryManager;
    
    @Override
    public String execute() {
        
        categories = categoryManager.findByIsEmail(isEmail());
        
        return SUCCESS;
    }

    public String save() {
        
        final Category cat = getCategory();
        if(StringUtils.isEmpty(cat.getName()) || StringUtils.isEmpty(cat.getDisplayQuery())) {
            errorMsg = "請輸入類別名稱和關鍵字條件";
        }
        else {
            if(cat.getId() != null) {
                categoryManager.update(cat);
            }
            else {
                cat.setEmail(isEmail());
                categoryManager.insert(cat);
            }
        }
        return execute();
    }
    
    public String delete() {
        
        categoryManager.delete(getCategory().getId());
        return execute();
    }
    
    public String updateColor() {
        
        Category cat = categoryManager.findById(category.getId());
        cat.setColor(category.getColor());
        categoryManager.update(cat);
        return "color";
    }
    
    public List<Category> getCategories() {
        return categories;
    }
    
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    protected void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    
    public String getErrorMsg() {
        return errorMsg;
    }
    
    protected CategoryManager getCategoryManager() {
        return categoryManager;
    }
    
    public abstract boolean isEmail();
}
