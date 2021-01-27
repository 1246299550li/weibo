package com.liheyu.weibo.bean;

public class UserBean {

    /**
     * code : 200
     * msg : 登陆成功！
     * data : {"user_id":15,"user_nickname":"我是新用户","user_sex":1,"user_intro":"","user_icon":"http"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_id : 15
         * user_nickname : 我是新用户
         * user_sex : 1
         * user_intro :
         * user_icon : http
         */

        private int user_id;
        private String user_nickname;
        private int user_sex;
        private String user_intro;
        private String user_icon;

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public int getUser_sex() {
            return user_sex;
        }

        public void setUser_sex(int user_sex) {
            this.user_sex = user_sex;
        }

        public String getUser_intro() {
            return user_intro;
        }

        public void setUser_intro(String user_intro) {
            this.user_intro = user_intro;
        }

        public String getUser_icon() {
            return user_icon;
        }

        public void setUser_icon(String user_icon) {
            this.user_icon = user_icon;
        }
    }
}
