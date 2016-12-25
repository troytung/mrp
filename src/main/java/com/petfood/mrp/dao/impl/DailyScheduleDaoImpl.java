package com.petfood.mrp.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.petfood.mrp.dao.DailyScheduleDao;
import com.petfood.mrp.model.DailySchedule;

@Repository
@Profile("!test")
public class DailyScheduleDaoImpl extends JdbcDaoSupport implements DailyScheduleDao {

    private static final Logger log = LoggerFactory.getLogger(DailyScheduleDaoImpl.class);
    private final SimpleJdbcInsert dailyScheduleInsert;
    private final DailyScheduleRowMapper dsrm;
    private final ResultSetExtractor<DailySchedule> resultSetExtractor;

    @Autowired
    public DailyScheduleDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
        dailyScheduleInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("DAILY_SCHEDULE");
        dsrm = new DailyScheduleRowMapper();
        resultSetExtractor = new ResultSetExtractor<DailySchedule>() {

            @Override
            public DailySchedule extractData(ResultSet rst) throws SQLException, DataAccessException {

                if (rst.next()) {
                    return dsrm.mapRow(rst, 1);
                }
                else {
                    return null;
                }
            }
        };
    }

    private class DailyScheduleRowMapper implements RowMapper<DailySchedule> {

        @Override
        public DailySchedule mapRow(ResultSet rst, int row) throws SQLException {

            final DailySchedule ds = new DailySchedule();
            ds.setSchedule_dt(rst.getDate("schedule_dt"));
            ds.setPro_code(rst.getString("pro_code"));
            ds.setTarget_amt(rst.getInt("target_amt"));
            ds.setProduced_amt(rst.getInt("produced_amt"));
            ds.setPacked_amt(rst.getInt("packed_amt"));
            ds.setCreate_by(rst.getString("create_by"));
            ds.setCreate_dt(rst.getTimestamp("create_dt"));
            ds.setModify_by(rst.getString("modify_by"));
            ds.setModify_dt(rst.getTimestamp("modify_dt"));
            ds.setCus_name(rst.getString("cus_name"));
            ds.setPro_name(rst.getString("pro_name"));
            return ds;
        }
    }

    @Override
    public int insert(DailySchedule ds) {
        MapSqlParameterSource sps = new MapSqlParameterSource();
        sps.addValue("SCHEDULE_DT", ds.getSchedule_dt());
        sps.addValue("PRO_CODE", ds.getPro_code());
        sps.addValue("TARGET_AMT", ds.getTarget_amt());
        sps.addValue("PRODUCED_AMT", ds.getProduced_amt());
        sps.addValue("PACKED_AMT", ds.getPacked_amt());
        sps.addValue("CREATE_BY", ds.getCreate_by());
        sps.addValue("CREATE_DT", new java.util.Date());
        return dailyScheduleInsert.execute(sps);
    }

    @Override
    public List<DailySchedule> getByScheduleDt(Date schedule_dt) {
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT C.CUS_NAME, B.PRO_NAME, A.* FROM DAILY_SCHEDULE A ");
        sql.append("INNER JOIN PRODUCT B ON A.PRO_CODE = B.PRO_CODE ");
        sql.append("INNER JOIN CUSTOMER C ON B.CUS_CODE = C.CUS_CODE ");
        sql.append("WHERE A.SCHEDULE_DT = ? ");
        return getJdbcTemplate().query(sql.toString(), dsrm, schedule_dt);
    }

    @Override
    public int deleteByScheduleDt(Date schedule_dt) {
        return getJdbcTemplate().update("DELETE FROM DAILY_SCHEDULE WHERE SCHEDULE_DT = ? ", schedule_dt);
    }

}
