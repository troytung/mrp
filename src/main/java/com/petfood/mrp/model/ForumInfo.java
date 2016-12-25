package com.petfood.mrp.model;

public class ForumInfo {

    private final String forumId;
    private final Brand brand;
    private final String forumName;

    public ForumInfo(String forumId, Brand brand, String forumName) {

        this.forumId = forumId;
        this.brand = brand;
        this.forumName = forumName;
    }

    public String getForumId() {
        return forumId;
    }

    public Brand getBrand() {
        return brand;
    }

    public String getForumName() {
        return forumName;
    }

}
