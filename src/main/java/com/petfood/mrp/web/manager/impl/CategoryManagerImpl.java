
package com.petfood.mrp.web.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petfood.mrp.dao.CategoryDao;
import com.petfood.mrp.model.Category;
import com.petfood.mrp.web.manager.CategoryManager;

@Service
public class CategoryManagerImpl implements CategoryManager {

    @Autowired
    private CategoryDao categoryDao;
    
    @Override
    public int insert(Category category) {
        return categoryDao.insert(category);
    }

    @Override
    public int update(Category category) {
        return categoryDao.update(category);
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public Category findById(Long id) {
        return categoryDao.findById(id);
    }

    @Override
    public int delete(Long id) {
        return categoryDao.delete(id);
    }

    @Override
    public List<Category> findByIsEmail(boolean email) {
        return categoryDao.findByIsEmail(email);
    }

    @Override
    public int countCategories(boolean email) {
        return categoryDao.countCategories(email);
    }

}
