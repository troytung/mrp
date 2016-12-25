package com.petfood.mrp.util;

import java.util.Calendar;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 都要實作 "序號邏輯"
 */
public class SerialNumberGenerator implements SerialNumberedInserter.SerialNumberGenerator {

    JdbcTemplate jdbcTemplate;
    String tableName;
    String serialNumberName;
    String timeCreatedName;
    String functionName;

    /**
     * 建構子，在這裡將序號產生需要的參數都傳進來
     */
    public SerialNumberGenerator(JdbcTemplate jdbcTemplate, // spring jdbcTemplate
            String tableName, // 資料表的名稱
            String serialNumberName, // 你取的序號欄位的名稱
            String timeCreatedName, // 資料建立的時間的欄位名稱
            String functionName) { // 序號的前兩碼功能代碼
        this.jdbcTemplate = jdbcTemplate;
        this.functionName = functionName;
        this.tableName = tableName;
        this.serialNumberName = serialNumberName;
        this.timeCreatedName = timeCreatedName;
    }

    /**
     * 把資料庫中，目前最大的序號，找出來，informix找不到時，會回傳0
     *
     * 解析SQL： MAX(%3$s) -> MAX(serialNumberName)： 先把最大筆的資料找出來，這樣資料庫的效能會比較好
     *
     * CAST( SUBSTR(MAX(%3$s), -5) AS int) -> 把序號 TW1000900001 取出來為 00001，並轉為數字
     *
     * timeCreatedName >= MDY(MONTH(TODAY), 1, YEAR(TODAY))： 大於每個月1號，每個月流水號要從新開始 (注意：這是INFORMIX的寫法，其它資料庫未必符合)
     *
     * %3$s LIKE ? -> serialNumberName LIKE TW10009%： 這樣寫會讓效能比較好，不需要特別拆字去比較
     */
    private static String COUNT_SQL = "SELECT CAST(SUBSTR(MAX(%3$s), -5) AS int) FROM %1$s" + " WHERE %2$s >= MDY(MONTH(TODAY), 1, YEAR(TODAY))"
            + " AND %3$s LIKE ?";

    /**
     * 實作 "序號邏輯.序號產生" 的方法
     */
    public String generate() {
        String serialNumber = null;
        String prefix;
        int count;
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR) - 1911; // 改成中國年
        prefix = String.format("%1$s%2$03d%3$tm", functionName, year, new Date()); // java.util Formatter
        // 取得目前資料庫中最大的序號
        count = jdbcTemplate.queryForObject(String.format(COUNT_SQL, tableName, timeCreatedName, serialNumberName), Integer.class, prefix + "%");
        return String.format("%s%05d", prefix, count + 1); // 回傳目前資料庫最大的序號加1，沒有資料會回傳0
    }
}