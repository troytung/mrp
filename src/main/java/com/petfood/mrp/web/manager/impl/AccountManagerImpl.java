package com.petfood.mrp.web.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petfood.mrp.dao.JdbcDao;
import com.petfood.mrp.model.User;
import com.petfood.mrp.web.manager.AccountManager;

@Service
public class AccountManagerImpl implements AccountManager {

    private static final Logger log = LoggerFactory.getLogger(AccountManagerImpl.class);

    @Autowired
    private JdbcDao jdbcDao;

    @Override
    public void createUser(User user) {
        jdbcDao.insertUser(user);
    }

    @Override
    public User getUser(String userId) {
        return jdbcDao.getUser(userId);
    }

    @Override
    public List<User> getAllUsers() {
        return jdbcDao.getAllUsers();
    }

    @Override
    public void updateUser(User user) {
        jdbcDao.updateUser(user);
    }

    // @Override
    // public List<User> findByReceiveEmail(boolean receiveEmail) {
    // return jdbcDao.findByReceiveEmail(receiveEmail);
    // }

}
