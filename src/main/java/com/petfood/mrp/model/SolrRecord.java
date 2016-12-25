package com.petfood.mrp.model;

import java.util.Collection;

import org.joda.time.DateTime;

import com.petfood.mrp.model.site.Site;

public class SolrRecord {

    private String id;
    private Site site;
    private Brand brand;
    private String model;
    private String topicText;
    private String topicId;
    private String postId;
    private String postText;
    private Integer postNumber;
    private String posterId;
    private String poster;
    private DateTime postDate;
    private Long postDateLong;
    private double positive;
    private double negative;
    private boolean reply;
    private Collection<String> posNegInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTopicText() {
        return topicText;
    }

    public void setTopicText(String topicText) {
        this.topicText = topicText;
    }

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public Integer getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(Integer postNumber) {
        this.postNumber = postNumber;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public DateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(DateTime postDate) {
        this.postDate = postDate;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public double getPositive() {
        return positive;
    }

    public void setPositive(double positive) {
        this.positive = positive;
    }

    public double getNegative() {
        return negative;
    }

    public void setNegative(double negative) {
        this.negative = negative;
    }
    
    public Long getPostDateLong() {
        return postDateLong;
    }

    public void setPostDateLong(Long postDateLong) {
        this.postDateLong = postDateLong;
    }

    public static void setRecordId(SolrRecord record) {
        record.setId(Post.generateUUID(record.getSite(), record.getTopicId(), record.getPostId(), record.getPostNumber()));
    }

    public Collection<String> getPosNegInfo() {
        return posNegInfo;
    }

    public void setPosNegInfo(Collection<String> posNegInfo) {
        this.posNegInfo = posNegInfo;
    }

}
