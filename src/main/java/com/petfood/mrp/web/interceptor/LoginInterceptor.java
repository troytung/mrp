package com.petfood.mrp.web.interceptor;

import javax.servlet.http.HttpServletRequest;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.petfood.mrp.web.action.AbstractAction;
import com.petfood.mrp.web.model.Login;

public class LoginInterceptor extends AbstractInterceptor {

    public static final String LOGIN_SESSION_KEY = LoginInterceptor.class.getCanonicalName() + ".login";

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {

        ActionContext ctx = invocation.getInvocationContext();
        final Login login = (Login) ctx.getSession().get(LOGIN_SESSION_KEY);
        String result;
        if (login == null) {
            HttpServletRequest req = (HttpServletRequest) ctx.get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
            if ("XMLHttpRequest".equals(req.getHeader("X-Requested-With"))) {
                result = "ajaxlogin";
            }
            else {
                result = "login";
            }
        }
        else {
            final Object actObj = invocation.getAction();
            if (actObj instanceof AbstractAction) {
                ((AbstractAction) actObj).setLogin(login);
            }
            result = invocation.invoke();
        }
        return result;
    }

}
