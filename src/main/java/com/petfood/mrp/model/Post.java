package com.petfood.mrp.model;

import java.text.MessageFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.joda.time.DateTime;

import com.petfood.mrp.model.site.Site;

public class Post {

    private static final MessageFormat idFormat = new MessageFormat("{0}_{1}_{2}_{3}");
    private String hashId;
    private Site site;
    private String topicId;
    private String postId;
    private boolean reply;
    private Integer postNumber;
    private String text;
    private String poster;
    private String posterId;
    private DateTime postDate;
    private Long postDateLong;

    public Long getPostDateLong() {
        return postDateLong;
    }

    public void setPostDateLong(Long postDateLong) {
        this.postDateLong = postDateLong;
    }

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
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

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public Integer getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(Integer postNumber) {
        this.postNumber = postNumber;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public DateTime getPostDate() {
        return postDate;
    }

    public void setPostDate(DateTime postDate) {
        this.postDate = postDate;
    }

    public static void setHashId(Post post) {
        post.setHashId(generateUUID(post.getSite(), post.getTopicId(), post.getPostId(), post.getPostNumber()));
    }

    static String generateUUID(Site site, String topicId, String postId, Integer postNumber) {
        return idFormat.format(new String[] { site.toString(), topicId, postId, postNumber.toString() });
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }

}
