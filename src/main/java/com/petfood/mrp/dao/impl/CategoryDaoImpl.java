
package com.petfood.mrp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.petfood.mrp.dao.CategoryDao;
import com.petfood.mrp.model.Category;

@Repository
public class CategoryDaoImpl extends JdbcDaoSupport implements CategoryDao {
    
    private final SimpleJdbcInsert categoryInsert;
    private final CategoryRowMapper crm;
    private final ResultSetExtractor<Category> resultSetExtractor;
    
    @Autowired
    public CategoryDaoImpl(DataSource datasource) {
        
        setDataSource(datasource);
        categoryInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("CATEGORY").usingGeneratedKeyColumns("id");
        crm = new CategoryRowMapper();
        resultSetExtractor = new ResultSetExtractor<Category>() {

            @Override
            public Category extractData(ResultSet rst) throws SQLException, DataAccessException {
                
                if(rst.next()) {
                    return crm.mapRow(rst, 1);
                }
                else {
                    return null;
                }
            }
            
        };
    }
    
    @Override
    public int insert(Category category) {
        
        Map<String, Object> m = new HashMap<>(3);
        m.put("name", category.getName());
        m.put("display_query", category.getDisplayQuery());
        m.put("query", category.getQuery());
        m.put("separated_query", category.getSeparatedQuery());
        m.put("color", category.getColor());
        m.put("email", category.isEmail());
        Long id = categoryInsert.executeAndReturnKey(m).longValue();
        category.setId(id);
        return 1;
    }

    @Override
    public int update(Category category) {
        String sql = "UPDATE CATEGORY SET NAME = ?, DISPLAY_QUERY = ?, QUERY = ?, SEPARATED_QUERY = ?, COLOR = ?, EMAIL = ? WHERE ID = ?";
        return getJdbcTemplate().update(sql, category.getName(), category.getDisplayQuery(), category.getQuery(), category.getSeparatedQuery(), category.getColor(), category.isEmail(), category.getId());
    }

    @Override
    public List<Category> findAll() {
        return getJdbcTemplate().query("SELECT * FROM CATEGORY ORDER BY ID", crm);
    }
    
    @Override
    public List<Category> findByIsEmail(boolean email) {
        return getJdbcTemplate().query("SELECT * FROM CATEGORY WHERE EMAIL = ? ORDER BY ID", new Object[]{email}, crm);
    }
    
    @Override
    public int countCategories(boolean email) {
        return getJdbcTemplate().queryForObject("SELECT COUNT(1) FROM CATEGORY WHERE EMAIL = ?", new Object[]{email}, Integer.class); 
    }

    @Override
    public Category findById(Long id) {
        
        return getJdbcTemplate().query("SELECT * FROM CATEGORY WHERE ID = ?", new Object[]{id}, resultSetExtractor);
    }
    
    @Override
    public int delete(Long id) {
        return getJdbcTemplate().update("DELETE FROM CATEGORY WHERE ID = ?", id);
    }

    private class CategoryRowMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rst, int row) throws SQLException {
           
            final Category c = new Category();
            c.setId(rst.getLong("id"));
            c.setName(rst.getString("name"));
            c.setDisplayQuery(rst.getString("display_query"));
            c.setQuery(rst.getString("query"));
            c.setSeparatedQuery(rst.getString("separated_query"));
            c.setColor(rst.getString("color"));
            c.setEmail(rst.getBoolean("email"));
            return c;
        }
    }
}
