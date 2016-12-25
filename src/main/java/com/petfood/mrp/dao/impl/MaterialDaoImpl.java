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

import com.petfood.mrp.dao.MaterialDao;
import com.petfood.mrp.model.Material;

@Repository
@Profile("!test")
public class MaterialDaoImpl extends JdbcDaoSupport implements MaterialDao {

    private static final Logger log = LoggerFactory.getLogger(MaterialDaoImpl.class);
    private final SimpleJdbcInsert materialInsert;

    @Autowired
    public MaterialDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
        materialInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("MATERIAL");
    }

    @Override
    public List<Material> getMaterialsBySuppCode(String suppCode) {
        return getJdbcTemplate().query("SELECT * FROM MATERIAL WHERE SUPP_CODE = ? ", new BeanPropertyRowMapper<Material>(Material.class), suppCode);
    }

}
