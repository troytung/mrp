package com.petfood.mrp.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.petfood.mrp.model.Article;
import com.petfood.mrp.model.Category;

public class ExportExcelUtil {

    private static final Logger log = LoggerFactory.getLogger(ExportExcelUtil.class);
    private static final DateTimeFormatter DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
            .withLocale(Locale.US);
    private static final String BREADCRUMB_FORMAT = "{0} > {1}";
    public static final String NUMBER_FORMAT = "#,##0.00";

    private static void createCell(Row row, int columnNum, String cellValue) {
        createCell(row, columnNum, cellValue, null);
    }

    private static void createCell(Row row, int columnNum, String cellValue, CellStyle cellStyle) {
        Cell cell = row.createCell(columnNum);
        cell.setCellValue(cellValue);
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
    }

    /**
     * 傳入文章列表，回傳代表excel的Workbook物件
     * 
     * @param row
     * @param columnNum
     * @param cellValue
     * @param cellStyle
     */
    public static Workbook exportWorkbook(Category cat, List<Article> arts) throws Exception {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("report");
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(IndexedColors.YELLOW.index);

        Row row = sheet.createRow(0);
        createCell(row, 0, "監測主題", cellStyle);
        createCell(row, 1, "標題", cellStyle);
        createCell(row, 2, "發佈時間", cellStyle);
        createCell(row, 3, "主文/回文", cellStyle);
        createCell(row, 4, "討論串總則數", cellStyle);
        createCell(row, 5, "作者", cellStyle);
        createCell(row, 6, "內容", cellStyle);
        // createCell(row, 7, "來源", cellStyle);
        createCell(row, 7, "來源網站", cellStyle);
        createCell(row, 8, "點閱數", cellStyle);
        createCell(row, 9, "正面強度", cellStyle);
        createCell(row, 10, "負面強度", cellStyle);
        createCell(row, 11, "原始連結", cellStyle);
        int rowNum = 1;
        for (Article a : arts) {
            row = sheet.createRow(rowNum);
            createCell(row, 0, cat.getName());
            createCell(row, 1, a.getTopic());
            createCell(row, 2, DATETIME_FORMAT.print(a.getPostDate()));
            createCell(row, 3, (a.isReply() ? "回文" : "主文"));
            createCell(row, 4, a.getReplyCnt() != null ? a.getReplyCnt().toString() : "");
            createCell(row, 5, a.getPoster());
            createCell(row, 6, a.getText());
            // createCell(row, 7, "討論區");
            createCell(row, 7, getSiteForumName(a));
            createCell(row, 8, (a.getViewCnt() == null ? NumberUtils.INTEGER_ZERO : a.getViewCnt()).toString());
            createCell(row, 9, String.valueOf(a.getPositive()));
            createCell(row, 10, String.valueOf(a.getNegative()));
            createCell(row, 11, a.getSite() == null ? "" : (a.getSite()
                    .getPostUrl(a.getForumId(), a.getTopicId(), a.getPostNumber(), a.getPostId())));
            rowNum++;
        }
        return workbook;
    }

    public static String getSiteForumName(Article article) {
        try {
            return MessageFormat.format(BREADCRUMB_FORMAT, article.getSite().getSiteName(), article.getSite()
                    .getForumInfoMap().get(article.getForumId()).getForumName());
        }
        catch (Exception e) {
            log.warn("error occured while formatting site forum name: " + ToStringBuilder.reflectionToString(article, ToStringStyle.DEFAULT_STYLE), e);
            return null;
        }
    }

    /**
     * 傳入文章列表，回傳代表excel檔案的InputStream
     * 
     * @param arts
     * @return
     * @throws
     * @throws Exception
     */
    public static InputStream exportInputStream(Category cat, List<Article> arts) throws Exception {

        InputStream excelStream = null;
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()){
            exportWorkbook(cat, arts).write(baos);
            
            baos.flush();
            byte[] buffer = baos.toByteArray();
            excelStream = new ByteArrayInputStream(buffer, 0, buffer.length);
        }
        return excelStream;
    }
    
    public static void exportInputStreamToFile(Category cat, List<Article> arts, String filename) throws Exception {

        
        try(final FileOutputStream fos = new FileOutputStream(filename);
            final OutputStream os = new BufferedOutputStream(fos)) {
            exportWorkbook(cat, arts).write(os);
        }
        
    }

//    public static void main(String... args) {
//        String s = "2013-08-02 20:53:58";
//        DateTimeFormatter df1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(
//                DateTimeZone.forID("Asia/Hong_Kong"));
//        DateTimeFormatter df2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZone(
//                DateTimeZone.forID("Africa/Asmara"));
//
//        System.out.println(df1.parseDateTime(s).toDate());
//        System.out.println(df2.parseDateTime(s).toDate());
//        System.out.println(df1.parseLocalDateTime(s).toDate());
//        System.out.println(df2.parseLocalDateTime(s).toDate());
//        // System.out.println(DateTimeZone.getAvailableIDs());
//    }
}
