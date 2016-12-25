package com.petfood.mrp.web.converter;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

@SuppressWarnings("rawtypes")
public class JavaSqlDateConverter extends StrutsTypeConverter {

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Converts one or more String values to the specified class.
     * 
     * @param context
     *            the action context
     * @param values
     *            the String values to be converted, such as those submitted from an HTML form
     * @param toClass
     *            the class to convert to
     * @return the converted object
     * 
     */
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {

        Date returnObject = null;
        String value = values[0];
        if (StringUtils.isNotEmpty(value)) {
            try {
                returnObject = new Date(DATE_FORMAT.parse(value).getTime());
            }
            catch (ParseException e) {
                // Just to ignore the parse exception
            }
        }
        return returnObject;
    }

    /**
     * Converts the specified object to a String.
     * 
     * @param context
     *            the action context
     * @param o
     *            the object to be converted
     * @return the converted String
     */
    @Override
    public String convertToString(Map context, Object o) {

        return DATE_FORMAT.format(o);
    }

}
