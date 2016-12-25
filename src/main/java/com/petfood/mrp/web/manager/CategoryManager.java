
package com.petfood.mrp.web.manager;

import java.util.List;

import com.petfood.mrp.model.Category;

public interface CategoryManager {

    int insert(Category category);
    
    int update(Category category);
    
    List<Category> findAll();
    
    List<Category> findByIsEmail(boolean email);
    
    int countCategories(boolean email);
    
    Category findById(Long id);
    
    int delete(Long id);
}
