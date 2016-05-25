package com.zhning.shareproj.entity;

import java.util.List;

/**
 * Created by zhning on 2016/5/24.
 */
public class PostFollow {
    long id;
    long postId;
    long userId;
    String content;
    int reply;
    boolean read;
    int floor;
    List<PostComment> postCommentList;

    public PostFollow(long id, long postId, long userId, String content, int floor) {
        this.id = id;
        this.postId = postId;
        this.userId = userId;
        this.content = content;
        this.floor = floor;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public List<PostComment> getPostCommentList() {
        return postCommentList;
    }

    public void setPostCommentList(List<PostComment> postCommentList) {
        this.postCommentList = postCommentList;
    }
}

