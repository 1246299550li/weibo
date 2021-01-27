package com.liheyu.weibo.bean;

public class Comment {
    private int commentId;
    private String commentUserNickname;
    private String commentUserIcon;
    private String commentContent;
    private String commentTime;
    private int commentLikeNum;
    private boolean commentIsLike;

    public Comment(int commentId, String commentUserNickname, String commentUserIcon, String commentContent, String commentTime, int commentLikeNum, boolean commentIsLike) {
        this.commentId = commentId;
        this.commentUserNickname = commentUserNickname;
        this.commentUserIcon = commentUserIcon;
        this.commentContent = commentContent;
        this.commentTime = commentTime;
        this.commentLikeNum = commentLikeNum;
        this.commentIsLike = commentIsLike;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentUserNickname() {
        return commentUserNickname;
    }

    public void setCommentUserNickname(String commentUserNickname) {
        this.commentUserNickname = commentUserNickname;
    }

    public String getCommentUserIcon() {
        return commentUserIcon;
    }

    public void setCommentUserIcon(String commentUserIcon) {
        this.commentUserIcon = commentUserIcon;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public int getCommentLikeNum() {
        return commentLikeNum;
    }

    public void addCommentLikeNum() {
        commentLikeNum++;
    }

    public void setCommentLikeNum(int commentLikeNum) {
        this.commentLikeNum = commentLikeNum;
    }

    public boolean getCommentIsLike() {
        return commentIsLike;
    }

    public void setCommentIsLike(boolean commentIsLike) {
        this.commentIsLike = commentIsLike;
    }
}
