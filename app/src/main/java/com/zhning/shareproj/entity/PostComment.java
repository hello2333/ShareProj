package com.zhning.shareproj.entity;

/**
 * Created by zhning on 2016/5/24.
 */
public class PostComment {
    long id;
    long postFollowId;
    long userId;
    long toUserId;
    String content;
    boolean read;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostFollowId() {
        return postFollowId;
    }

    public void setPostFollowId(long postFollowId) {
        this.postFollowId = postFollowId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getToUserId() {
        return toUserId;
    }

    public void setToUserId(long toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
