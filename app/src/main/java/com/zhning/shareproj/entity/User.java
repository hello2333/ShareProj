package com.zhning.shareproj.entity;

import java.util.List;

/**
 * Created by baidu on 16/5/5.
 */
public class User {
    long userId;
    int portrait;
    String name;
    String description;
    int age;
    boolean male;
    String industry;
    String university;
    String hometown;
    int status;
    List<String> labels;
    String hobby;

    public User(long userId, int portrait, String name, String description, int age, boolean male,
                String industry, String university, String hometown, int status, String hobby) {
        this.userId = userId;
        this.portrait = portrait;
        this.name = name;
        this.description = description;
        this.age = age;
        this.male = male;
        this.industry = industry;
        this.university = university;
        this.hometown = hometown;
        this.status = status;
        this.hobby = hobby;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getHometown() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown = hometown;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
