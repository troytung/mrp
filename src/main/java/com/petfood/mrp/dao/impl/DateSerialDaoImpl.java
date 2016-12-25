package com.petfood.mrp.dao.impl;

import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.petfood.mrp.dao.DateSerialDao;

@Repository
@Profile("!test")
public class DateSerialDaoImpl extends JdbcDaoSupport implements DateSerialDao {

    private static final Logger log = LoggerFactory.getLogger(DateSerialDaoImpl.class);

    @Autowired
    public DateSerialDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
    }

    @Override
    public String getDateSerial(String dateser_colname) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(getJdbcTemplate()).withProcedureName("SP_GET_DATE_SERIAL");
        SqlParameterSource in = new MapSqlParameterSource().addValue("I_DATESER_COLNAME", dateser_colname).addValue("O_DATESER_CODE", null);
        Map<String, Object> res = jdbcCall.execute(in);
        return res.get("O_DATESER_CODE").toString();
    }

}
