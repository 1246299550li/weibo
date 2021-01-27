package com.liheyu.weibo.net;

import com.liheyu.weibo.bean.Comment;
import com.liheyu.weibo.bean.Weibo;

import java.util.ArrayList;
import java.util.List;

public class InfoLab {

    private boolean isLogin;

    private static InfoLab instance;

    private ArrayList<Weibo> weiboList;

    private int userId;

    private int userSex;

    private String userNickname;

    private String userIntro;

    private String userIcon;

    private String userDevice;


    public static InfoLab getInstance() {
        if (instance == null) {
            instance = new InfoLab();
        }
        return instance;
    }

    public InfoLab() {
        this.weiboList = new ArrayList<>();
        this.isLogin = false;
        userDevice = "华为";
    }

    public void clear() {
        weiboList.clear();
    }

    public Weibo findWeiboById(int id) {
        for (Weibo w : weiboList) {
            if (w.getWeiboId() == id) {
                return w;
            }

        }
        return null;
    }

    public Comment findCommentById(int WeiboId, int commentId) {
        Weibo w = findWeiboById(WeiboId);
        for (Comment c : w.getComments()) {
            if (c.getCommentId() == commentId) {
                return c;
            }
        }
        return null;
    }

    public ArrayList<Weibo> getWeiboList() {
        return weiboList;
    }

    public void setWeiboList(ArrayList<Weibo> weiboList) {
        this.weiboList = weiboList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void addDate(List<Weibo> w) {
        weiboList.addAll(w);
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public String getUserIntro() {
        return userIntro;
    }

    public void setUserIntro(String userIntro) {
        this.userIntro = userIntro;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getUserDevice() {
        return userDevice;
    }

    public void setUserDevice(String userDevice) {
        this.userDevice = userDevice;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
