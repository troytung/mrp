package com.petfood.mrp.util;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;

public class SerialNumberedInserter {

    JdbcTemplate jdbcTemplate;
    SerialNumberGenerator generator;
    int maxRetry;

    /**
     * 看有幾種不同的序號邏輯，都要繼承這個介面
     */
    public interface SerialNumberGenerator {
        String generate();
    }

    /**
     * 建構子，可以自訂重覆取號的最大次數
     * 
     * @param jdbcTemplate
     *            對資料庫存取的jdbcTemplate
     * @param generator
     *            產生流水號的邏輯，實作Generator介面
     * @param maxRetry
     *            最多重複嘗試產生流水號的次數
     */
    public SerialNumberedInserter(JdbcTemplate jdbcTemplate, SerialNumberGenerator generator, int maxRetry) {
        this.jdbcTemplate = jdbcTemplate;
        this.generator = generator;
        this.maxRetry = maxRetry;
    }

    /**
     * 建構子，預設可以重覆取號三次
     * 
     * @param jdbcTemplate
     *            對資料庫存取的jdbcTemplate
     * @param generator
     *            產生流水號的邏輯，實作Generator介面
     */
    public SerialNumberedInserter(JdbcTemplate jdbcTemplate, SerialNumberGenerator generator) {
        this(jdbcTemplate, generator, 3);
    }

    /**
     * @param sql
     *            要執行新增的sql，如： INSERT INTO foo (id, serial_number) VALUES( ?, ? )
     * @param param
     *            jdbcTemplate要執行sql時需要的參數，如： Object[] param = new Object[]{ foo_seq, "" ); 序號的地方要傳空值。
     * @param serialNumberPos
     *            序號欄位在param中的第幾位(第一位是0)。
     * @param retries
     *            目前因失敗已重複嘗試產生序號的次數
     */

    private void tryInsert(String sql, Object[] param, int serialNumberPos, int retries) {
        param[serialNumberPos] = generator.generate(); // 取號
        try {
            jdbcTemplate.update(sql, param);
        }
        catch (DataIntegrityViolationException ex) {
            if (retries < maxRetry) {
                tryInsert(sql, param, serialNumberPos, retries + 1);
            }
            throw ex;
        }
    }

    /**
     * 產生序號，並執行資料新增
     * 
     * @param sql
     *            要執行新增的sql，如： INSERT INTO foo (id, serial_number) VALUES( ?, ? )
     * @param param
     *            jdbcTemplate要執行sql時需要的參數，如： Object[] param = new Object[]{ foo_seq, "" ); 序號的地方要傳空值。
     * @param serialNumberPos
     *            序號欄位在param中的第幾位(第一位是0)
     */

    public String insert(String sql, Object[] param, int serialNumberPos) {
        tryInsert(sql, param, serialNumberPos, 0);
        // 如果有需要將產生的序號，存到另一個TABLE當FK時，可以將序號傳回
        return param[serialNumberPos].toString();
    }

}