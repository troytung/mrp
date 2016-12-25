package com.petfood.mrp.dao.impl;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.petfood.mrp.dao.CustomerDao;
import com.petfood.mrp.model.Customer;

@Repository
@Profile("!test")
public class CustomerDaoImpl extends JdbcDaoSupport implements CustomerDao {

    private static final Logger log = LoggerFactory.getLogger(CustomerDaoImpl.class);
    private final SimpleJdbcInsert customerInsert;

    @Autowired
    public CustomerDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
        customerInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("CUSTOMER");
    }

    @Override
    public List<Customer> getAllCustomers() {
        return getJdbcTemplate().query("SELECT * FROM CUSTOMER", new BeanPropertyRowMapper<Customer>(Customer.class));
    }

}
