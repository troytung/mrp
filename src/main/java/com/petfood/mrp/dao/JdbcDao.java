package com.petfood.mrp.dao;

import java.util.LinkedHashMap;
import java.util.List;

import org.joda.time.DateTime;

import com.petfood.mrp.model.Article;
import com.petfood.mrp.model.Post;
import com.petfood.mrp.model.PostDateCnt;
import com.petfood.mrp.model.Topic;
import com.petfood.mrp.model.User;
import com.petfood.mrp.model.site.Site;

public interface JdbcDao {

    void savePosts(List<Post> posts);

    void saveTopic(Topic topic);

    List<Post> getPosts(List<String> hashIds);

    List<Article> getArticles(List<String> hashIds);

    List<Article> getArticles(LinkedHashMap<String, Article> hashIdMap);

    DateTime getMaxModifyDate(Site site, String forumId);

    Integer getMaxPostNumber(Site site, String topicId);

    List<PostDateCnt> getPostCnt(final DateTime beginDate, final DateTime endDate);

    void insertUser(User user);

    User getUser(String userId);

    List<User> getAllUsers();

    void updateUser(User u);

    // List<User> findByReceiveEmail(boolean receiveEmail);

}
