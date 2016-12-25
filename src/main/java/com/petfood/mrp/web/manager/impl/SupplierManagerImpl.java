package com.petfood.mrp.web.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petfood.mrp.dao.SupplierDao;
import com.petfood.mrp.model.Supplier;
import com.petfood.mrp.web.manager.SupplierManager;

@Service
public class SupplierManagerImpl implements SupplierManager {

    private static final Logger log = LoggerFactory.getLogger(SupplierManagerImpl.class);

    @Autowired
    private SupplierDao supplierDao;

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierDao.getAllSuppliers();
    }

}
