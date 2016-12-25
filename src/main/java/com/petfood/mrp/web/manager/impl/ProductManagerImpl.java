package com.petfood.mrp.web.manager.impl;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petfood.mrp.dao.DateSerialDao;
import com.petfood.mrp.dao.ProMaterialDao;
import com.petfood.mrp.dao.ProductDao;
import com.petfood.mrp.model.ProMaterial;
import com.petfood.mrp.model.Product;
import com.petfood.mrp.web.manager.ProductManager;

@Service
public class ProductManagerImpl implements ProductManager {

    private static final Logger log = LoggerFactory.getLogger(ProductManagerImpl.class);

    @Autowired
    private DateSerialDao dateSerialDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProMaterialDao proMaterialDao;

    @Override
    public void insert(Product p, List<ProMaterial> proMats) {
        String createBy = p.getCreate_by();
        String proCode = (p.getPro_code() == null || "".equals(p.getPro_code())) ? dateSerialDao.getDateSerial("PRODUCT.PRO_CODE") : p.getPro_code();
        p.setPro_code(proCode);
        if (productDao.update(p) == 0) {
            productDao.insert(p);
        }
        proMaterialDao.deleteByProCode(proCode);
        proMats.removeAll(Collections.singleton(null));
        proMats.stream().forEach(pm -> {
            pm.setPro_code(proCode);
            pm.setCreate_by(createBy);
            proMaterialDao.insert(pm);
        });
    }

    @Override
    public List<Product> searchProduct(String cusCode, String proName) {
        return productDao.searchProduct(cusCode, proName);
    }

    @Override
    public Product getProductByProCode(String proCode) {
        return productDao.getProductByProCode(proCode);
    }

    @Override
    public List<ProMaterial> getMatsByProCode(String pro_code) {
        return proMaterialDao.getMatsByProCode(pro_code);
    }

}
