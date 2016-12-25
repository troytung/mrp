package com.petfood.mrp.web.action;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.petfood.mrp.model.User;
import com.petfood.mrp.util.BCrypt;
import com.petfood.mrp.web.manager.AccountManager;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.accountAction")
public class AccountAction extends AbstractAction {

    private static final Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", Pattern.CASE_INSENSITIVE);

    @Autowired
    private AccountManager accountManager;

    private List<User> users;
    private User user;
    private String message;
    private boolean edit;

    private String prepareUserList() {
        users = accountManager.getAllUsers();
        return SUCCESS;
    }

    @Override
    public String execute() {
        if (user != null && user.getUserCode() != null) {
            edit = true;
            user = accountManager.getUser(user.getUserCode());
        }
        else {
            user = new User();
        }
        return prepareUserList();
    }

    // public String edit() {
    // edit = true;
    // user = accountManager.getUser(user.getUserId());
    // return prepareUserList();
    // }

    private void setupPassword() {
        String salt = UUID.randomUUID().toString();
        user.setSalt(salt);
        String pw = user.getPassword() + salt;
        user.setPassword(BCrypt.hashpw(pw, BCrypt.gensalt(12)));
    }

    public String save() {
        edit = true;
        if (isValid()) {
            if (StringUtils.isNotEmpty(user.getPassword())) {
                setupPassword();
            }
            accountManager.updateUser(user);
            message = "編輯帳號成功";
            edit = false;
            return execute();
        }
        else {
            return prepareUserList();
        }
    }

    public String create() {
        if (isValid()) {
            setupPassword();
            accountManager.createUser(user);
            message = "建立帳號成功";
            return execute();
        }
        else {
            return prepareUserList();
        }
    }

    private boolean isValid() {

        boolean valid = true;
        user.setUserName(user.getUserName() == null ? null : user.getUserName().trim());
        if (StringUtils.isEmpty(user.getUserName())) {
            user.setUserNameErr("請輸入名稱");
            valid = false;
        }
        user.setEmail(user.getEmail() == null ? null : user.getEmail().trim());
        if (StringUtils.isEmpty(user.getEmail())) {
            user.setEmailErr("請輸入 Email");
            valid = false;
        }
        else if (!emailPattern.matcher(user.getEmail()).matches()) {
            user.setEmailErr("Email 格式錯誤");
            valid = false;
        }
        if (!edit) {
            user.setUserCode(user.getUserCode() == null ? null : user.getUserCode().trim());
            if (StringUtils.isEmpty(user.getUserCode())) {
                user.setUserIdErr("請輸入帳號");
                valid = false;
            }
            else if (accountManager.getUser(user.getUserCode()) != null) {
                user.setUserIdErr("帳號重覆，請輸入別的帳號");
                valid = false;
            }
        }
        user.setPassword(user.getPassword() == null ? null : user.getPassword().trim());
        if (!edit || (edit && StringUtils.isNotEmpty(user.getPassword()))) {
            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPasswordErr("請輸入密碼");
                valid = false;
            }
            user.setConfirmPassword(user.getConfirmPassword() == null ? null : user.getConfirmPassword().trim());
            if (StringUtils.isEmpty(user.getConfirmPassword())) {
                user.setConfirmPasswordErr("請再次輸入密碼");
                valid = false;
            }
            else if (!user.getConfirmPassword().equals(user.getPassword())) {
                user.setConfirmPasswordErr("與密碼不符，請重新輸入");
                valid = false;
            }
        }

        return valid;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEdit() {
        return edit;
    }

}
