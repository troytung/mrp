package com.petfood.mrp.web.action;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;

import com.petfood.mrp.model.Category;
import com.petfood.mrp.web.manager.CategoryManager;
import com.petfood.mrp.web.manager.QueryManager;

public abstract class AbstractSearchAction extends AbstractAction {

    public static final MessageFormat QUERY_TEMPLATE = new MessageFormat("'{'!q.op={0} df=text'}' ({1})");
    private static final int FIFTEEN_DAYS = 1;
    private static final int THIRTY_DAYS = 2;
    private static final int SPECIFIC_MONTH = 3;
    private static final int DATE_RANGE = 4;

    protected static final DateTimeFormatter YEAR_MONTH_FORMAT = DateTimeFormat.forPattern("yyyy/MM").withLocale(Locale.US);
    protected static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy/MM/dd").withLocale(Locale.US);

    @Autowired
    private QueryManager queryManager;
    @Autowired
    private CategoryManager categoryManager;

    private int dateType = FIFTEEN_DAYS;
    private String yearMonth;
    private DateTime beginDate;
    private DateTime endDate;
    protected long searchDate;
    private List<String> yearMonths;
    private List<Category> categories;

    protected void prepareYearMonthList() {

        final DateTime dt = DateTime.now();
        yearMonths = new ArrayList<>(2);
        yearMonths.add(dt.minusMonths(1).toString(YEAR_MONTH_FORMAT));
        yearMonths.add(dt.minusMonths(2).toString(YEAR_MONTH_FORMAT));
        categories = categoryManager.findByIsEmail(false);
       
    }

    protected DateTime[] getDateTimeQuiteria() {

        DateTime[] dts = new DateTime[2];
        if (dateType == FIFTEEN_DAYS) {
            dts[0] = DateTime.now().minusDays(15).withTimeAtStartOfDay();
            dts[1] = DateTime.now().withTimeAtStartOfDay();
        }
        else if (dateType == THIRTY_DAYS) {
            dts[0] = DateTime.now().minusDays(30).withTimeAtStartOfDay();
            dts[1] = DateTime.now().withTimeAtStartOfDay();
        }
        else if (dateType == SPECIFIC_MONTH) {
            final String[] sp = StringUtils.split(yearMonth, '/');
            final int year = Integer.parseInt(sp[0]);
            final int month = Integer.parseInt(sp[1]);

            DateTime dt = new DateTime(year, month, 1, 0, 0, 0, 0);
            dts[0] = dt;
            dts[1] = dt.dayOfMonth().withMaximumValue();

        }
        else if (dateType == DATE_RANGE) {
            dts[0] = beginDate == null ? getSearchMinDateTime() : beginDate;
            dts[1] = endDate == null ? DateTime.now().withTimeAtStartOfDay() : endDate;
        }

        return dts;
    }

    protected String formatQuery(Long categoryId, String defaultLogicOp, String extraQuery) {
        
        final Category cat = getCategoryManager().findById(categoryId);
        return formatQuery(cat, defaultLogicOp, extraQuery);
    }
    
    protected String formatQuery(Category category, String defaultLogicOp, String extraQuery) {
        
        String finalQuery = QUERY_TEMPLATE.format(new String[] {defaultLogicOp, category.getQuery()});
        if (extraQuery != null && !extraQuery.trim().equals("")) {
            finalQuery += " AND (" + extraQuery + ")";
        }
        
        return finalQuery;
    }
    

    private DateTime getSearchMinDateTime() {
        return DateTime.now().minusMonths(3);
    }
    
    public String getSearchMinDate() {
        return getSearchMinDateTime().toString(DATE_FORMAT);
    }

    public int getDateType() {
        return dateType;
    }

    public void setDateType(int dateType) {
        this.dateType = dateType;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public DateTime getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(DateTime beginDate) {
        this.beginDate = beginDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public long getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(long searchDate) {
        this.searchDate = searchDate;
    }

    public List<String> getYearMonths() {
        return yearMonths;
    }

    public List<Category> getCategories() {
        return categories;
    }

    protected void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    protected CategoryManager getCategoryManager() {
        return categoryManager;
    }

    protected QueryManager getQueryManager() {
        return queryManager;
    }

}
