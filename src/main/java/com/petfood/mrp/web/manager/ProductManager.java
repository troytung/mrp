package com.petfood.mrp.web.manager;

import java.util.List;

import com.petfood.mrp.model.ProMaterial;
import com.petfood.mrp.model.Product;

public interface ProductManager {

    void insert(Product p, List<ProMaterial> proMats);

    List<Product> searchProduct(String cusCode, String proName);

    Product getProductByProCode(String proCode);

    List<ProMaterial> getMatsByProCode(String pro_code);

}
