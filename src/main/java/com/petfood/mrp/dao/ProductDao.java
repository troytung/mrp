package com.petfood.mrp.dao;

import java.util.List;

import com.petfood.mrp.model.Product;

public interface ProductDao {

    int insert(Product product);

    List<Product> searchProduct(String cusCode, String proName);

    Product getProductByProCode(String proCode);

    int update(Product product);

}
