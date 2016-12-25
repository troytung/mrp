package com.petfood.mrp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

import com.petfood.mrp.dao.ProductDao;
import com.petfood.mrp.model.Product;

@Repository
@Profile("!test")
public class ProductDaoImpl extends JdbcDaoSupport implements ProductDao {

    private static final Logger log = LoggerFactory.getLogger(ProductDaoImpl.class);
    private final SimpleJdbcInsert productInsert;
    private final ProductRowMapper prm;
    private final ResultSetExtractor<Product> resultSetExtractor;

    @Autowired
    public ProductDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
        productInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("PRODUCT");
        prm = new ProductRowMapper();
        resultSetExtractor = new ResultSetExtractor<Product>() {

            @Override
            public Product extractData(ResultSet rst) throws SQLException, DataAccessException {

                if (rst.next()) {
                    return prm.mapRow(rst, 1);
                }
                else {
                    return null;
                }
            }
        };
    }

    private class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rst, int row) throws SQLException {

            final Product p = new Product();
            p.setPro_code(rst.getString("pro_code"));
            p.setPro_name(rst.getString("pro_name"));
            p.setCus_code(rst.getString("cus_code"));
            p.setCus_name(rst.getString("cus_name"));
            p.setPro_desc(rst.getString("pro_desc"));
            p.setCreate_by(rst.getString("create_by"));
            p.setCreate_dt(rst.getTimestamp("create_dt"));
            p.setModify_by(rst.getString("modify_by"));
            p.setModify_dt(rst.getTimestamp("modify_dt"));
            return p;
        }
    }

    @Override
    public int insert(Product p) {
        MapSqlParameterSource sps = new MapSqlParameterSource();
        sps.addValue("PRO_CODE", p.getPro_code());
        sps.addValue("PRO_NAME", p.getPro_name());
        sps.addValue("CUS_CODE", p.getCus_code());
        sps.addValue("PRO_DESC", p.getPro_desc());
        sps.addValue("CREATE_BY", p.getCreate_by());
        sps.addValue("CREATE_DT", new Date());
        return productInsert.execute(sps);
    }

    @Override
    public List<Product> searchProduct(String cusCode, String proName) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("");
        sql.append("SELECT B.CUS_NAME, A.* FROM PRODUCT A INNER JOIN CUSTOMER B ON A.CUS_CODE = B.CUS_CODE WHERE 1 = 1 ");
        if (cusCode != null && !"".equals(cusCode)) {
            sql.append("AND A.CUS_CODE = ? ");
            params.add(cusCode);
        }
        if (proName != null && !"".equals(proName)) {
            sql.append("AND A.PRO_NAME LIKE ? ");
            params.add("%" + proName + "%");
        }
        return getJdbcTemplate().query(sql.toString(), params.toArray(), prm);
    }

    @Override
    public Product getProductByProCode(String proCode) {
        return getJdbcTemplate().query("SELECT B.CUS_NAME, A.* FROM PRODUCT A INNER JOIN CUSTOMER B ON A.CUS_CODE = B.CUS_CODE WHERE A.PRO_CODE = ? ",
                resultSetExtractor, proCode);
    }

    @Override
    public int update(Product product) {
        return getJdbcTemplate().update("UPDATE PRODUCT SET PRO_DESC = ? WHERE PRO_CODE = ? ", product.getPro_desc(), product.getPro_code());
    }

}
