package com.petfood.mrp.web.converter;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


@SuppressWarnings("rawtypes") 
public class JodaDateTimeConverter extends StrutsTypeConverter {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy/MM/dd");
    /**
     * Converts one or more String values to the specified class.
     * 
     * @param context
     *            the action context
     * @param values
     *            the String values to be converted, such as those submitted
     *            from an HTML form
     * @param toClass
     *            the class to convert to
     * @return the converted object
     * 
     */
    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {

        DateTime returnObject = null;
        String value = values[0];
        if (StringUtils.isNotEmpty(value)) {
            try {
                returnObject = DATE_FORMAT.parseDateTime(value);
            }
            catch (RuntimeException e) {
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

        return DATE_FORMAT.print((DateTime) o);
    }

}
