package com.petfood.mrp.model;

import org.joda.time.DateTime;

import com.petfood.mrp.model.site.Site;

public class PostDateCnt {

    private Site site;
    private DateTime post_date;
    private Integer posts;

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public DateTime getPost_date() {
        return post_date;
    }

    public void setPost_date(DateTime post_date) {
        this.post_date = post_date;
    }

    public Integer getPosts() {
        return posts;
    }

    public void setPosts(Integer posts) {
        this.posts = posts;
    }

}
