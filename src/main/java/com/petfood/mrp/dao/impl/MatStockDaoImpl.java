package com.petfood.mrp.dao.impl;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.petfood.mrp.dao.MatStockDao;
import com.petfood.mrp.model.MatStock;

@Repository
@Profile("!test")
public class MatStockDaoImpl extends JdbcDaoSupport implements MatStockDao {

    private static final Logger log = LoggerFactory.getLogger(MatStockDaoImpl.class);
    private final SimpleJdbcInsert matStockInsert;

    @Autowired
    public MatStockDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
        matStockInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("MAT_STOCK");
    }

    // @Override
    // public List<Material> getMaterialsBySuppCode(String suppCode) {
    // return getJdbcTemplate().query("SELECT * FROM MATERIAL WHERE SUPP_CODE = ? ", new BeanPropertyRowMapper<Material>(Material.class), suppCode);
    // }

    @Override
    public void adjMatStock(MatStock ms) {
        // TODO Auto-generated method stub
        if (ms.isAdd()) {// 入庫

        }
        else {// 出庫

        }
    }

}
