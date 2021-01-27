package com.liheyu.weibo.bean;

import com.liheyu.weibo.bean.Comment;

import java.util.ArrayList;

public class Weibo {
    private int weiboId;
    private String weiboUserNickname;
    private String weiboUserIcon;
    private String weiboTime;
    private int weiboType;
    private String weiboForwardComment;
    private String weiboContent;
    private String weiboImg;
    private int weiboForwardNum;
    private int weiboCommentNum;
    private int weiboLikeNum;
    private boolean isLike;
    private ArrayList<Comment> comments;

    public Weibo(int weiboId, String weiboUserNickname, String weiboUserIcon, String weiboTime, int weiboType, String weiboForwardComment, String weiboContent, String weiboImg, int weiboForwardNum, int weiboCommentNum, int weiboLikeNum, boolean isLike) {
        this.weiboId = weiboId;
        this.weiboUserNickname = weiboUserNickname;
        this.weiboUserIcon = weiboUserIcon;
        this.weiboTime = weiboTime;
        this.weiboType = weiboType;
        this.weiboForwardComment = weiboForwardComment;
        this.weiboContent = weiboContent;
        this.weiboImg = weiboImg;
        this.weiboForwardNum = weiboForwardNum;
        this.weiboCommentNum = weiboCommentNum;
        this.weiboLikeNum = weiboLikeNum;
        this.isLike = isLike;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public String getWeiboUserNickname() {
        return weiboUserNickname;
    }

    public void setWeiboUserNickname(String weiboUserNickname) {
        this.weiboUserNickname = weiboUserNickname;
    }

    public String getWeiboUserIcon() {
        return weiboUserIcon;
    }

    public void setWeiboUserIcon(String weiboUserIcon) {
        this.weiboUserIcon = weiboUserIcon;
    }

    public String getWeiboTime() {
        return weiboTime;
    }

    public void setWeiboTime(String weiboTime) {
        this.weiboTime = weiboTime;
    }

    public int getWeiboType() {
        return weiboType;
    }

    public void setWeiboType(int weiboType) {
        this.weiboType = weiboType;
    }

    public String getWeiboForwardComment() {
        return weiboForwardComment;
    }

    public void setWeiboForwardComment(String weiboForwardComment) {
        this.weiboForwardComment = weiboForwardComment;
    }

    public String getWeiboContent() {
        return weiboContent;
    }

    public void setWeiboContent(String weiboContent) {
        this.weiboContent = weiboContent;
    }

    public String getWeiboImg() {
        return weiboImg;
    }

    public void setWeiboImg(String weiboImg) {
        this.weiboImg = weiboImg;
    }

    public int getWeiboForwardNum() {
        return weiboForwardNum;
    }

    public void setWeiboForwardNum(int weiboForwardNum) {
        this.weiboForwardNum = weiboForwardNum;
    }

    public int getWeiboCommentNum() {
        return weiboCommentNum;
    }

    public void setWeiboCommentNum(int weiboCommentNum) {
        this.weiboCommentNum = weiboCommentNum;
    }

    public void addWeiboCommentNum() {
        this.weiboCommentNum++;
    }

    public int getWeiboLikeNum() {
        return weiboLikeNum;
    }

    public void setWeiboLikeNum(int weiboLikeNum) {
        this.weiboLikeNum = weiboLikeNum;
    }

    public void addWeiboLikeNum() {
        this.weiboLikeNum++;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
