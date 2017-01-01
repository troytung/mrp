package com.petfood.mrp.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.petfood.mrp.model.User;
import com.petfood.mrp.util.BCrypt;
import com.petfood.mrp.web.interceptor.LoginInterceptor;
import com.petfood.mrp.web.manager.AccountManager;
import com.petfood.mrp.web.model.Login;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.loginAction")
public class LoginAction extends AbstractAction {

    // private Map<String, Object> session;
    private String account;
    private String password;
    private String errorMessage;

    @Autowired
    private AccountManager accountManager;

    @Override
    public String execute() {
        return SUCCESS;
    }

    public String login() {

        boolean pass = false;
        User user = accountManager.getUser(account);
        if (user != null) {
            if (user.isActive()) {
                String plainPw = password == null ? null : password + user.getSalt();
                pass = BCrypt.checkpw(plainPw, user.getPassword());
            }
            else {
                errorMessage = "帳號停用中";
                return "input";
            }
        }

        // //FIXME
        // pass = true;
        // user = new User();
        // user.setAdmin(true);
        // user.setUserCode("troy");
        // user.setUserName("troy");

        if (pass) {
            final Login login = new Login();
            login.setUserCode(user.getUserCode());
            login.setUserName(user.getUserName());
            login.setAdmin(user.isAdmin());
            ActionContext.getContext().getSession().put(LoginInterceptor.LOGIN_SESSION_KEY, login);
            return "chain";
        }
        else {
            errorMessage = "帳號或密碼錯誤";
            return "input";
        }
    }

    public String logout() {
        ActionContext ctx = ActionContext.getContext();
        Login login = (Login) ctx.getSession().get(LoginInterceptor.LOGIN_SESSION_KEY);
        login.logout();
        ctx.getSession().remove(LoginInterceptor.LOGIN_SESSION_KEY);
        return SUCCESS;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    // @Override
    // public void setSession(Map<String, Object> session) {
    // this.session = session;
    // }

}
