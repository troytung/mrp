package com.petfood.mrp.manager;

import java.util.List;

import org.joda.time.DateTime;

import com.petfood.mrp.model.Article;
import com.petfood.mrp.model.Brand;
import com.petfood.mrp.model.Post;
import com.petfood.mrp.model.Topic;
import com.petfood.mrp.model.site.Site;

public interface BizManager {

    /**
     * 
     * Insert or update posts to all repositories.
     * @param posts
     * @param topicText
     * @param brand
     * @param site
     * @param model
     */
    void savePosts(List<Post> posts, String topicText, Brand brand, Site site, String model);

    void saveTopic(Topic topic);

    List<Post> getPosts(List<String> hashIds);

    List<Article> getArticles(List<String> hashIds);

    DateTime getMaxModifyDate(Site site, String forumId);

    Integer getMaxPostNumber(Site site, String topicId);

//    PageContainer<Article> getArticles(SolrQuery query, DateTime beginDate, DateTime endDate, int page, int rowsPerPage);
//
//    List<XYData<DateTime, Long>> queryForChart(SolrQuery query, DateTime beginDate, DateTime endDate);

}
