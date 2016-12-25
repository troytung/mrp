package com.petfood.mrp.web.manager;

import java.util.List;

import com.petfood.mrp.model.User;

public interface AccountManager {

    public void createUser(User user);

    public User getUser(String userId);

    public List<User> getAllUsers();

    public void updateUser(User user);

    // public List<User> findByReceiveEmail(boolean receiveEmail);

}
