package com.petfood.mrp.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import com.petfood.mrp.dao.JdbcDao;
import com.petfood.mrp.model.Article;
import com.petfood.mrp.model.Post;
import com.petfood.mrp.model.PostDateCnt;
import com.petfood.mrp.model.Topic;
import com.petfood.mrp.model.User;
import com.petfood.mrp.model.site.Site;

@Repository
@Profile("test")
public class JdbcDaoTestImpl implements JdbcDao {

    @Override
    public void savePosts(List<Post> posts) {
        for (final Post p : posts) {
            System.out.println("===============posts ignored==============");
        }
    }

    @Override
    public void saveTopic(Topic topic) {
        System.out.println("=================topic ignored============");
    }

    @Override
    public List<Post> getPosts(List<String> hashIds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DateTime getMaxModifyDate(Site site, String forumId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Article> getArticles(List<String> hashIds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Integer getMaxPostNumber(Site site, String topicId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Article> getArticles(LinkedHashMap<String, Article> hashIdMap) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PostDateCnt> getPostCnt(DateTime beginDate, DateTime endDate) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insertUser(User user) {
        // TODO Auto-generated method stub

    }

    @Override
    public User getUser(String userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void updateUser(User u) {
        // TODO Auto-generated method stub

    }

    // @Override
    // public List<User> findByReceiveEmail(boolean receiveEmail) {
    // // TODO Auto-generated method stub
    // return null;
    // }

}
