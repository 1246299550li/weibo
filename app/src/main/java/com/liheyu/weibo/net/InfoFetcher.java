package com.liheyu.weibo.net;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;


import com.google.gson.Gson;
import com.liheyu.weibo.bean.Comment;
import com.liheyu.weibo.bean.UserBean;
import com.liheyu.weibo.bean.Weibo;
import com.liheyu.weibo.bean.WeiboListBean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InfoFetcher {

    public static final String API_ADRESS = "http://119.23.53.70";

    private OkHttpClient client = new OkHttpClient();

    //注册
    public boolean register(String account, String pwd) {
        System.out.println("账号：" + account);
        System.out.println("密码：" + pwd);
        RequestBody body = new FormBody.Builder()
                .add("user_account", account)
                .add("user_pwd", pwd)
                .add("user_sex", String.valueOf(1))
                .build();
        Request request = new Request.Builder()
                .url(API_ADRESS + "/user/register")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            System.out.println(res);
            Gson gson = new Gson();
            UserBean userBean = gson.fromJson(res, UserBean.class);
            System.out.println("状态码：" + userBean.getCode());
            if (userBean.getCode() == 200) {
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //登录
    public void login(String account, String pwd) {
        System.out.println("账号：" + account);
        System.out.println("密码：" + pwd);
        RequestBody body = new FormBody.Builder()
                .add("user_account", account)
                .add("user_pwd", pwd)
                .build();
        Request request = new Request.Builder()
                .url(API_ADRESS + "/user/login")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            System.out.println(res);
            Gson gson = new Gson();
            UserBean userBean = gson.fromJson(res, UserBean.class);
            System.out.println("状态码：" + userBean.getCode());
            if (userBean.getCode() == 200) {
                InfoLab.getInstance().setLogin(true);
                InfoLab.getInstance().setUserId(userBean.getData().getUser_id());
                InfoLab.getInstance().setUserNickname(userBean.getData().getUser_nickname());
                InfoLab.getInstance().setUserSex(userBean.getData().getUser_sex());
                InfoLab.getInstance().setUserIntro(userBean.getData().getUser_intro());
                InfoLab.getInstance().setUserIcon(asyncGet(userBean.getData().getUser_icon(), "user"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //转发微博
    public void forwardWeibo(String comment, String id, String device) {
        String userId = String.valueOf(InfoLab.getInstance().getUserId());

        RequestBody body = new FormBody.Builder()
                .add("weibo_user_id", userId)
                .add("weibo_id", id)
                .add("weibo_forward_comment", comment)
                .add("weibo_device", device)
                .build();
        Request request = new Request.Builder()
                .url(API_ADRESS + "/weibo/forward")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //发微博
    public void writeWeibo(String content, String img, String device) {
        String userId = String.valueOf(InfoLab.getInstance().getUserId());
        File file = new File(img);

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("weibo_user_id", userId)
                .addFormDataPart("weibo_content", content)
                .addFormDataPart("weibo_device", device)
                .addFormDataPart("weibo_img", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        Request request = new Request.Builder()
                .url(API_ADRESS + "/weibo/add")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //评论
    public void comment(String id, String content) {
        String userId = String.valueOf(InfoLab.getInstance().getUserId());

        RequestBody body = new FormBody.Builder()
                .add("user_id", userId)
                .add("weibo_id", id)
                .add("content", content)
                .build();
        Request request = new Request.Builder()
                .url(API_ADRESS + "/weibo/comment")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //点赞 1=微博 2=评论
    public void like(int type, int id) {
        String userId = String.valueOf(InfoLab.getInstance().getUserId());

        RequestBody body = new FormBody.Builder()
                .add("user_id", userId)
                .add("type", String.valueOf(type))
                .add("id", String.valueOf(id))
                .build();
        Request request = new Request.Builder()
                .url(API_ADRESS + "/weibo/like")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //修改个人信息
    public void update(String userIcon, String userNickname, String userSex, String userIntro) {
        String userId = String.valueOf(InfoLab.getInstance().getUserId());
        File file = new File(userIcon);
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_id", userId)
                .addFormDataPart("user_nickname", userNickname)
                .addFormDataPart("user_intro", userIntro)
                .addFormDataPart("user_sex", userSex)
                .addFormDataPart("user_icon", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build();
        Request request = new Request.Builder()
                .url(API_ADRESS + "/user/info")
                .post(body)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //获取微博列表并加入lab中
    public void getWeiboList() {
        String userId = String.valueOf(InfoLab.getInstance().getUserId());

        RequestBody body = new FormBody.Builder()
                .add("user_id", userId)
                .build();
        Request request = new Request.Builder()
                .url(API_ADRESS + "/weibo/all")
                .post(body)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String res = Objects.requireNonNull(response.body()).string();
            Log.e("p", res);
            Gson gson = new Gson();
            WeiboListBean weiboListBean = gson.fromJson(res, WeiboListBean.class);

            List<Weibo> weiboList = new ArrayList<>();
            for (WeiboListBean.DataBean i : weiboListBean.getData()) {
                Weibo w = new Weibo(i.getWeibo_id(), i.getUser_nickname(), asyncGet(i.getUser_icon(), i.getWeibo_id() + "icon"), toTimeString(i.getWeibo_time()),
                        i.getWeibo_type(), i.getWeibo_forward_comment(), i.getWeibo_content(), asyncGet(i.getWeibo_img(), i.getWeibo_id() + "img"),
                        i.getForward_num(), i.getComment_num(), i.getLike_num(), i.isIs_like());
                ArrayList<Comment> commentList = new ArrayList<>();
                for (WeiboListBean.DataBean.CommentListBean j : i.getComment_list()) {
                    commentList.add(new Comment(j.getComment_id(), j.getUser_nickname(), asyncGet(j.getUser_icon(), i.getWeibo_id() + "comment" + j.getComment_id()),
                            j.getComment_content(), toTimeString(j.getComment_time()), j.getComment_like_num(), j.isComment_is_like()));
                }
                w.setComments(commentList);
                System.out.println("添加");
                weiboList.add(w);
            }
            InfoLab.getInstance().clear();
            InfoLab.getInstance().addDate(weiboList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String asyncGet(String url, String fileName) {
        //请求超时设置
        client.newBuilder()
                .connectTimeout(2, TimeUnit.SECONDS)
                .readTimeout(2, TimeUnit.SECONDS).build();

        String res = null;
        final Request request = new Request.Builder().get()
                .url(InfoFetcher.API_ADRESS + url)
                .build();
        try {
            Response response = client.newCall(request).execute();
            byte[] bytes = response.body().bytes();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            File file = new File(dir + fileName + ".jpg");
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            res = file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Log.e("print", res);
        System.out.println(res);
        return res;
    }

    //将时间戳转化为时间
    @SuppressLint("SimpleDateFormat")
    private String toTimeString(int timestamp) {
        String ms = timestamp + "000";
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(Long.valueOf(ms)));
    }


    //32位MD5加密
    public String md5Decode32(String content) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(content.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UnsupportedEncodingException", e);
        }
        //对生成的16字节数组进行补零操作
        StringBuilder hex = new StringBuilder(hash.length * 2);
        for (byte b : hash) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

}
