package com.zhning.shareproj.entity;

/**
 * Created by baidu on 16/5/5.
 */
public class User {
    long userId;
    int portrait;
    String name;
    String description;

    public User(long userId, int portrait, String name, String description) {
        this.userId = userId;
        this.portrait = portrait;
        this.name = name;
        this.description = description;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getPortrait() {
        return portrait;
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
