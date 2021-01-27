package com.liheyu.weibo.bean;

import java.util.List;

public class WeiboListBean {

    /**
     * code : 200
     * msg : 微博列表
     * data : [{"weibo_id":1,"weibo_type":1,"weibo_content":"哈哈哈在干啥呀","user_nickname":"你大爷","user_icon":"http","weibo_forward_comment":"123","weibo_img":"http","weibo_time":2147483647,"weibo_device":"华为","forward_num":0,"like_num":0,"is_like":false,"comment_num":1,"comment_list":[{"comment_id":1,"user_nickname":"你大爷","user_icon":"http","comment_content":"hah","comment_time":2147483647,"comment_like_num":1,"comment_is_like":true}]},{"weibo_id":2,"weibo_type":1,"weibo_content":"我是微博内容","user_nickname":"xixi","user_icon":"/uploads/20191211/3a9c31e70f0cb2eda72d3bce751b6b89.jpg","weibo_forward_comment":null,"weibo_img":"/uploads/20191211/759760926c642be6bd14cf706181b66a.jpg","weibo_time":1575997718,"weibo_device":"华为","forward_num":1,"like_num":5,"is_like":true,"comment_num":4,"comment_list":[{"comment_id":2,"user_nickname":"我是新用户","user_icon":"http","comment_content":"我是评论内容","comment_time":1576035598,"comment_like_num":0,"comment_is_like":false},{"comment_id":3,"user_nickname":"我是新用户","user_icon":"http","comment_content":"我是评论内容","comment_time":1576056231,"comment_like_num":0,"comment_is_like":false},{"comment_id":4,"user_nickname":"我是新用户","user_icon":"http","comment_content":"我是评论内容","comment_time":1576056233,"comment_like_num":0,"comment_is_like":false},{"comment_id":5,"user_nickname":"我是新用户","user_icon":"http","comment_content":"我是评论内容","comment_time":1576056234,"comment_like_num":2,"comment_is_like":true}]},{"weibo_id":3,"weibo_type":2,"weibo_content":"我是微博内容","user_nickname":"xixi","user_icon":"/uploads/20191211/3a9c31e70f0cb2eda72d3bce751b6b89.jpg","weibo_forward_comment":"我是转发语","weibo_img":"/uploads/20191211/759760926c642be6bd14cf706181b66a.jpg","weibo_time":1575998756,"weibo_device":"华为","forward_num":0,"like_num":1,"is_like":true,"comment_num":0,"comment_list":[]},{"weibo_id":4,"weibo_type":1,"weibo_content":"我是微博内容","user_nickname":"xixi","user_icon":"/uploads/20191211/3a9c31e70f0cb2eda72d3bce751b6b89.jpg","weibo_forward_comment":null,"weibo_img":"/uploads/20191211/05a8943d273c4e54b39cada46c511ee0.jpg","weibo_time":1576068619,"weibo_device":"华为","forward_num":0,"like_num":0,"is_like":false,"comment_num":0,"comment_list":[]},{"weibo_id":5,"weibo_type":1,"weibo_content":"我是微博内容","user_nickname":"xixi","user_icon":"/uploads/20191211/3a9c31e70f0cb2eda72d3bce751b6b89.jpg","weibo_forward_comment":null,"weibo_img":"/uploads/20191211/6175c5e87cd7270275b56f4c9ed9ae3b.jpg","weibo_time":1576068621,"weibo_device":"华为","forward_num":8,"like_num":0,"is_like":false,"comment_num":0,"comment_list":[]},{"weibo_id":6,"weibo_type":2,"weibo_content":"我是微博内容","user_nickname":"我是新用户","user_icon":"http","weibo_forward_comment":"我是转发语","weibo_img":"/uploads/20191211/6175c5e87cd7270275b56f4c9ed9ae3b.jpg","weibo_time":1576068642,"weibo_device":"华为","forward_num":0,"like_num":0,"is_like":false,"comment_num":0,"comment_list":[]},{"weibo_id":10,"weibo_type":2,"weibo_content":"我是微博内容","user_nickname":"我是新用户","user_icon":"http","weibo_forward_comment":"我是转发","weibo_img":"/uploads/20191211/6175c5e87cd7270275b56f4c9ed9ae3b.jpg","weibo_time":1576069026,"weibo_device":"华为","forward_num":0,"like_num":0,"is_like":false,"comment_num":0,"comment_list":[]},{"weibo_id":11,"weibo_type":2,"weibo_content":"我是微博内容","user_nickname":"你大爷","user_icon":"http","weibo_forward_comment":"我是转发","weibo_img":"/uploads/20191211/6175c5e87cd7270275b56f4c9ed9ae3b.jpg","weibo_time":1576069086,"weibo_device":"华为","forward_num":0,"like_num":0,"is_like":false,"comment_num":0,"comment_list":[]},{"weibo_id":12,"weibo_type":2,"weibo_content":"我是微博内容","user_nickname":"我是新用户","user_icon":"http","weibo_forward_comment":"我是转发","weibo_img":"/uploads/20191211/6175c5e87cd7270275b56f4c9ed9ae3b.jpg","weibo_time":1576069090,"weibo_device":"华为","forward_num":0,"like_num":0,"is_like":false,"comment_num":0,"comment_list":[]},{"weibo_id":13,"weibo_type":2,"weibo_content":"我是微博内容","user_nickname":"我是新用户","user_icon":"http","weibo_forward_comment":"我是转发","weibo_img":"/uploads/20191211/6175c5e87cd7270275b56f4c9ed9ae3b.jpg","weibo_time":1576069095,"weibo_device":"华为","forward_num":0,"like_num":0,"is_like":false,"comment_num":0,"comment_list":[]}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * weibo_id : 1
         * weibo_type : 1
         * weibo_content : 哈哈哈在干啥呀
         * user_nickname : 你大爷
         * user_icon : http
         * weibo_forward_comment : 123
         * weibo_img : http
         * weibo_time : 2147483647
         * weibo_device : 华为
         * forward_num : 0
         * like_num : 0
         * is_like : false
         * comment_num : 1
         * comment_list : [{"comment_id":1,"user_nickname":"你大爷","user_icon":"http","comment_content":"hah","comment_time":2147483647,"comment_like_num":1,"comment_is_like":true}]
         */

        private int weibo_id;
        private int weibo_type;
        private String weibo_content;
        private String user_nickname;
        private String user_icon;
        private String weibo_forward_comment;
        private String weibo_img;
        private int weibo_time;
        private String weibo_device;
        private int forward_num;
        private int like_num;
        private boolean is_like;
        private int comment_num;
        private List<CommentListBean> comment_list;

        public int getWeibo_id() {
            return weibo_id;
        }

        public void setWeibo_id(int weibo_id) {
            this.weibo_id = weibo_id;
        }

        public int getWeibo_type() {
            return weibo_type;
        }

        public void setWeibo_type(int weibo_type) {
            this.weibo_type = weibo_type;
        }

        public String getWeibo_content() {
            return weibo_content;
        }

        public void setWeibo_content(String weibo_content) {
            this.weibo_content = weibo_content;
        }

        public String getUser_nickname() {
            return user_nickname;
        }

        public void setUser_nickname(String user_nickname) {
            this.user_nickname = user_nickname;
        }

        public String getUser_icon() {
            return user_icon;
        }

        public void setUser_icon(String user_icon) {
            this.user_icon = user_icon;
        }

        public String getWeibo_forward_comment() {
            return weibo_forward_comment;
        }

        public void setWeibo_forward_comment(String weibo_forward_comment) {
            this.weibo_forward_comment = weibo_forward_comment;
        }

        public String getWeibo_img() {
            return weibo_img;
        }

        public void setWeibo_img(String weibo_img) {
            this.weibo_img = weibo_img;
        }

        public int getWeibo_time() {
            return weibo_time;
        }

        public void setWeibo_time(int weibo_time) {
            this.weibo_time = weibo_time;
        }

        public String getWeibo_device() {
            return weibo_device;
        }

        public void setWeibo_device(String weibo_device) {
            this.weibo_device = weibo_device;
        }

        public int getForward_num() {
            return forward_num;
        }

        public void setForward_num(int forward_num) {
            this.forward_num = forward_num;
        }

        public int getLike_num() {
            return like_num;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
        }

        public boolean isIs_like() {
            return is_like;
        }

        public void setIs_like(boolean is_like) {
            this.is_like = is_like;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public List<CommentListBean> getComment_list() {
            return comment_list;
        }

        public void setComment_list(List<CommentListBean> comment_list) {
            this.comment_list = comment_list;
        }

        public static class CommentListBean {
            /**
             * comment_id : 1
             * user_nickname : 你大爷
             * user_icon : http
             * comment_content : hah
             * comment_time : 2147483647
             * comment_like_num : 1
             * comment_is_like : true
             */

            private int comment_id;
            private String user_nickname;
            private String user_icon;
            private String comment_content;
            private int comment_time;
            private int comment_like_num;
            private boolean comment_is_like;

            public int getComment_id() {
                return comment_id;
            }

            public void setComment_id(int comment_id) {
                this.comment_id = comment_id;
            }

            public String getUser_nickname() {
                return user_nickname;
            }

            public void setUser_nickname(String user_nickname) {
                this.user_nickname = user_nickname;
            }

            public String getUser_icon() {
                return user_icon;
            }

            public void setUser_icon(String user_icon) {
                this.user_icon = user_icon;
            }

            public String getComment_content() {
                return comment_content;
            }

            public void setComment_content(String comment_content) {
                this.comment_content = comment_content;
            }

            public int getComment_time() {
                return comment_time;
            }

            public void setComment_time(int comment_time) {
                this.comment_time = comment_time;
            }

            public int getComment_like_num() {
                return comment_like_num;
            }

            public void setComment_like_num(int comment_like_num) {
                this.comment_like_num = comment_like_num;
            }

            public boolean isComment_is_like() {
                return comment_is_like;
            }

            public void setComment_is_like(boolean comment_is_like) {
                this.comment_is_like = comment_is_like;
            }
        }
    }
}
