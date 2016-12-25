package com.petfood.mrp.dao.impl;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.petfood.mrp.dao.ProMaterialDao;
import com.petfood.mrp.model.ProMaterial;

@Repository
@Profile("!test")
public class ProMaterialDaoImpl extends JdbcDaoSupport implements ProMaterialDao {

    private static final Logger log = LoggerFactory.getLogger(ProMaterialDaoImpl.class);
    private final SimpleJdbcInsert proMatInsert;

    @Autowired
    public ProMaterialDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
        proMatInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("PRO_MATERIAL");
    }

    @Override
    public int insert(ProMaterial pm) {
        MapSqlParameterSource sps = new MapSqlParameterSource();
        sps.addValue("PRO_CODE", pm.getPro_code());
        sps.addValue("MAT_CODE", pm.getMat_code());
        sps.addValue("AMOUNTS", pm.getAmounts());
        sps.addValue("CREATE_BY", pm.getCreate_by());
        sps.addValue("CREATE_DT", new Date());
        return proMatInsert.execute(sps);
    }

    @Override
    public void deleteByProCode(String pro_code) {
        this.getJdbcTemplate().update("DELETE FROM PRO_MATERIAL WHERE PRO_CODE = ? ", pro_code);
    }

    @Override
    public List<ProMaterial> getMatsByProCode(String pro_code) {
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT C.SUPP_CODE, C.SUPP_NAME, B.MAT_NAME, B.UNIT_NAME, A.* ");
        sql.append("FROM PRO_MATERIAL A ");
        sql.append("INNER JOIN MATERIAL B ON A.MAT_CODE = B.MAT_CODE ");
        sql.append("INNER JOIN SUPPLIER C ON B.SUPP_CODE = C.SUPP_CODE ");
        sql.append("WHERE A.PRO_CODE = ? ");
        return getJdbcTemplate().query(sql.toString(), new BeanPropertyRowMapper<ProMaterial>(ProMaterial.class), pro_code);
    }

}
