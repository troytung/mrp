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

import com.petfood.mrp.dao.SupplierDao;
import com.petfood.mrp.model.Supplier;

@Repository
@Profile("!test")
public class SupplierDaoImpl extends JdbcDaoSupport implements SupplierDao {

    private static final Logger log = LoggerFactory.getLogger(SupplierDaoImpl.class);
    private final SimpleJdbcInsert supplierInsert;

    @Autowired
    public SupplierDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
        supplierInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("SUPPLIER");
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return getJdbcTemplate().query("SELECT * FROM SUPPLIER", new BeanPropertyRowMapper<Supplier>(Supplier.class));
    }

}
