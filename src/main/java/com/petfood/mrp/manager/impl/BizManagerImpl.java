package com.petfood.mrp.manager.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.petfood.mrp.dao.JdbcDao;
import com.petfood.mrp.manager.BizManager;
import com.petfood.mrp.model.Article;
import com.petfood.mrp.model.Brand;
import com.petfood.mrp.model.Post;
import com.petfood.mrp.model.SolrRecord;
import com.petfood.mrp.model.Topic;
import com.petfood.mrp.model.site.Site;
import com.petfood.mrp.util.PositiveNegativeUtil;

@Service
public class BizManagerImpl implements BizManager {

    private static final Logger log = LoggerFactory.getLogger(BizManagerImpl.class);
    
    @Autowired
    private JdbcDao jdbcDao;
//    @Autowired
//    private SolrDao solrDao;

    @Override
    // @Transactional
    public void savePosts(List<Post> posts, String topicText, Brand brand, Site site, String model) {
        log.info("in savePosts");
        long start = System.currentTimeMillis();
        final List<SolrRecord> records = new ArrayList<>(posts.size());
        for(final Post p : posts) {
            
            p.setPostDateLong(p.getPostDate().withTimeAtStartOfDay().getMillis());
            records.add(postToRecord(p, topicText, brand, site, model));
        }
//        solrDao.updatePosts(records);
        jdbcDao.savePosts(posts);
        log.info("end savePosts, {} records, {} millis", posts.size(), (System.currentTimeMillis() - start));
    }
    
    private SolrRecord postToRecord(final Post post, String topicText, Brand brand, Site site, String model) {
        
        final SolrRecord r = new SolrRecord();
        
        r.setId(post.getHashId());
        r.setBrand(brand);
        r.setSite(site);
        r.setModel(model);
        r.setTopicText(topicText);
        r.setTopicId(post.getTopicId());
        r.setPostId(post.getPostId());
        r.setPostText(post.getText());
        r.setPostNumber(post.getPostNumber());
        r.setPosterId(post.getPosterId());
        r.setPoster(post.getPoster());
        r.setPostDate(post.getPostDate());
        r.setPostDateLong(post.getPostDateLong());
        r.setReply(post.isReply());
        PositiveNegativeUtil.setPositiveNegative(post.isReply() ? null : topicText, post.getText(), r);
        return r;
    }

    @Override
    @Transactional
    public void saveTopic(Topic topic) {
        jdbcDao.saveTopic(topic);
    }

    @Override
    public List<Post> getPosts(List<String> hashIds) {
        return jdbcDao.getPosts(hashIds);
    }

    @Override
    public List<Article> getArticles(List<String> hashIds) {
        return jdbcDao.getArticles(hashIds);
    }

    @Override
    public DateTime getMaxModifyDate(Site site, String forumId) {
        return jdbcDao.getMaxModifyDate(site, forumId);
    }

    @Override
    public Integer getMaxPostNumber(Site site, String topicId) {
        return jdbcDao.getMaxPostNumber(site, topicId);
    }

    

}