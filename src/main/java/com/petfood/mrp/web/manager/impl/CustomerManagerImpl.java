package com.petfood.mrp.web.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petfood.mrp.dao.CustomerDao;
import com.petfood.mrp.model.Customer;
import com.petfood.mrp.web.manager.CustomerManager;

@Service
public class CustomerManagerImpl implements CustomerManager {

    private static final Logger log = LoggerFactory.getLogger(CustomerManagerImpl.class);

    @Autowired
    private CustomerDao customerDao;

    @Override
    public List<Customer> getAllCustomers() {
        return customerDao.getAllCustomers();
    }

}
