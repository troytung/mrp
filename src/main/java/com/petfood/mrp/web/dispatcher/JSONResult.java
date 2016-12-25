/*
 * @(#)AbstractAction.java Jan 15, 2009
 *
 * Copyright (c) 2009- Business Intelligence Info. Tech.
 * 8F, No.222, Sec.1, FuXing S. Road,
 * Taipei, Taiwan
 * All rights reserved.
 */

package com.petfood.mrp.web.dispatcher;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.StrutsResultSupport;

import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionInvocation;

/**
 * 
 * For returning result into JSON format. parameters:
 * <ul>
 * <li><b>json (default) &lt;String&gt;</b>: the action attribute to get the
 * Object that need to be turned in to JSON</li>
 * <li><b>wrap &lt;Boolean&gt;</b>: [default value: {@code false}] whether the
 * {@code json} given, needs to be wapped in a JavaScript Object. The attribute
 * will be the value of id or the attribute value represented by the value of id
 * in the action. It is recommended to not to return a JSON in the form of an
 * Array. If {@code json} is a Java List, it is best to wrap it.</li>
 * <li><b>id &lt;String&gt;</b>: [default value: {@code "data"}] the value use
 * to represent the {@code json} if it is wrapped.</li>
 * <li><b>dateFormat &lt;String&gt;</b>: [default value: {@code null}] if given,
 * all the Date will be formated to String according to the format.</li>
 * <li><b>commentWrap &lt;Boolean&gt;</b>: [default value: {@code false}] if
 * true, the {@code json} will be wrapped with a comment for security issues. It
 * will need to be removed before fron end evaluates the JSON.</li>
 * <li><b>others &lt;String&gt;</b>: [default value: {@code null}] In the form
 * of comma separated String. This will only be used if {@code wrap == true}.
 * Values will be taken from the action/valuestack according the String parsed
 * and added to wrapped JSON.</li>
 * <li><b>charset &lt;String&gt;</b>: [default value: {@code "UTF-8"}] the
 * encoding to be used. Most case don't need to change this.</li>
 * </ul>
 * 
 * @author Steven
 * @since 0.0.1
 */
@SuppressWarnings("all")
public class JSONResult extends StrutsResultSupport {

    /** The default parameter */
    public static final String DEFAULT_PARAM = "json";
    
    private static final String EMPTY_OBJ = "{}";

    private String json = "json";

//    private boolean converted = false;
//    private String id = "data";
//    private String others = "";
//    private String charset = "UTF-8";
    private final Gson gson = new Gson();

    /**
     * default constructor
     */
    public JSONResult() {
        super();
    }

    public JSONResult(String json) {
        this();
        this.json = json;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {

//        if (!Charset.isSupported(getCharset())) {
//            throw new UnsupportedEncodingException("charset: " + getCharset() + " is not supported. If charset is unknown, default is UTF-8");
//        }

        HttpServletResponse response = ServletActionContext.getResponse();
        // response.setHeader("Cache-Control",
        // "no-store,no-cache,must-revalidate,post-check=0,pre-check=0");
        // response.setHeader("Pragma", "public");
        // response.setDateHeader("Expires", 0);

        response.setContentType("application/json;charset=UTF-8");

        Object jsonObj = invocation.getStack().findValue(conditionalParse(getJson(), invocation));
        String sb = EMPTY_OBJ;
        if (jsonObj != null) {
//            if (isConverted()) {
//                jsonObj = new JSONContainer(jsonObj.toString());
//            }
            
           
            sb = gson.toJson(jsonObj);
        }
        try (PrintWriter writer = response.getWriter()){
            writer.write(sb);
        }
    }

//    private Map<String, Object> wrapObj(Object jsonObj, ActionInvocation invocation) {
//
//        String id = conditionalParse(getId(), invocation);
//        if (id == null)
//            id = this.id;
//
//        String[] others = getOthers().split(",");
//
//        Map<String, Object> jsonWrapper = new HashMap<String, Object>(3 + others.length);
//        jsonWrapper.put("id", id);
//        jsonWrapper.put("result", invocation.getResultCode());
//        jsonWrapper.put(id, jsonObj);
//
//        Object obj;
//        for (String oKey : others) {
//            obj = invocation.getStack().findValue(oKey);
//            if (obj != null) {
//                jsonWrapper.put(oKey, obj);
//            }
//        }
//        return jsonWrapper;
//    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

//    public String getCharset() {
//        return charset;
//    }
//
//    public void setCharset(String charset) {
//        this.charset = charset;
//    }

    //    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public boolean isConverted() {
//        return converted;
//    }
//
//    public void setConverted(boolean converted) {
//        this.converted = converted;
//    }


}
