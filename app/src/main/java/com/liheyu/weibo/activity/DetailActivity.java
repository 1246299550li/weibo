package com.liheyu.weibo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liheyu.weibo.bean.Comment;
import com.liheyu.weibo.net.InfoFetcher;
import com.liheyu.weibo.net.InfoLab;
import com.liheyu.weibo.ui.InputTextDialog;
import com.liheyu.weibo.R;
import com.liheyu.weibo.bean.Weibo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private static final String EXTRA_WEATHER_ID = "com.liheyu.weibo.weibo_id";
    private RecyclerView mRecyclerView;
    private WeiboAdapter mAdapter;
    private LinearLayout mForward;
    private LinearLayout mComment;
    private LinearLayout mLike;
    private InputTextDialog mInputTextDialog;
    Bitmap bitmap;
    private int weiboId;

    public static Intent newIntent(Context packageContext, int id) {
        Intent intent = new Intent(packageContext, DetailActivity.class);
        intent.putExtra(EXTRA_WEATHER_ID, id);
        return intent;
    }

    @SuppressLint("StaticFieldLeak")
    private class LikeTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... params) {
            InfoFetcher infoFetcher = new InfoFetcher();
            infoFetcher.like(params[0], params[1]);
            return null;
        }

        //线程结束刷线UI
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(DetailActivity.this, R.string.likeSuccess, Toast.LENGTH_LONG).show();
            updateUI();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class CommentTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            InfoFetcher infoFetcher = new InfoFetcher();
            infoFetcher.comment(params[0], params[1]);
            return null;
        }

        //线程结束刷线UI
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(DetailActivity.this, R.string.commentSuccess, Toast.LENGTH_LONG).show();
            updateUI();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mRecyclerView = (RecyclerView) findViewById(R.id.comment_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        weiboId = getIntent().getIntExtra(EXTRA_WEATHER_ID, 0);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mInputTextDialog = new InputTextDialog(DetailActivity.this, R.style.dialog_center);
        mInputTextDialog.setmOnTextSendListener(new InputTextDialog.OnTextSendListener() {
            @Override
            public void onTextSend(String msg) {
                //点击发送按钮后，回调此方法，msg为输入的值
                new CommentTask().execute(String.valueOf(weiboId), msg);
                InfoLab.getInstance().findWeiboById(weiboId).addWeiboCommentNum();
                @SuppressLint("SimpleDateFormat")
                Comment c = new Comment(999, InfoLab.getInstance().getUserNickname(), InfoLab.getInstance().getUserIcon(), msg,
                        new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 0, false);
                InfoLab.getInstance().findWeiboById(weiboId).getComments().add(0, c);
            }
        });

        //转发按钮设置
        mForward = findViewById(R.id.detail_forward);
        mForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = WriteActivity.newIntent(DetailActivity.this, weiboId);
                startActivity(intent);
            }
        });

        //评论按钮设置
        mComment = findViewById(R.id.detail_comment);
        mComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mInputTextDialog.show();

            }
        });

        //点赞按钮设置
        mLike = findViewById(R.id.detail_like);
        final TextView isLikeText = findViewById(R.id.isLikeText);
        final ImageView isLikeImage = findViewById(R.id.isLikeImage);
        if (InfoLab.getInstance().findWeiboById(weiboId).isLike()) {
            isLikeText.setTextColor(Color.parseColor("#ff8140"));
            isLikeText.setText(R.string.liked);
            isLikeImage.setImageResource(R.drawable.like2);
        }
        mLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!InfoLab.getInstance().findWeiboById(weiboId).isLike()) {
                    //1=微博 2=评论  id
                    new LikeTask().execute(1, weiboId);
                    InfoLab.getInstance().findWeiboById(weiboId).addWeiboLikeNum();
                    isLikeText.setTextColor(Color.parseColor("#ff8140"));
                    isLikeText.setText(R.string.liked);
                    isLikeImage.setImageResource(R.drawable.like2);
                    InfoLab.getInstance().findWeiboById(weiboId).setLike(true);
                }
            }
        });


        updateUI();
    }

    //更新UI，新建adapter
    private void updateUI() {
        Weibo w = InfoLab.getInstance().findWeiboById(weiboId);
        mAdapter = new WeiboAdapter(w);
        mRecyclerView.setAdapter(mAdapter);
    }

    //第一个item的holder
    private class WeiboHolder extends RecyclerView.ViewHolder {
        private Weibo mWeibo;
        private ImageView mUserIcon;
        private TextView mUserNickname;
        private TextView mWeiboTime;
        private TextView mWeiboContent;
        private ImageView mWeiboImg;
        private TextView mForwardNum;
        private TextView mCommentNum;
        private TextView mLikeNum;
        private ImageView mIsLikeImage;

        public WeiboHolder(@NonNull View itemView) {
            super(itemView);
            mUserIcon = (ImageView) itemView.findViewById(R.id.user_icon);
            mUserNickname = (TextView) itemView.findViewById(R.id.user_nickname);
            mWeiboTime = (TextView) itemView.findViewById(R.id.weibo_time);
            mWeiboContent = (TextView) itemView.findViewById(R.id.weibo_content);
            mWeiboImg = (ImageView) itemView.findViewById(R.id.weibo_img);
            mForwardNum = (TextView) itemView.findViewById(R.id.forward_num);
            mCommentNum = (TextView) itemView.findViewById(R.id.comment_num);
            mLikeNum = (TextView) itemView.findViewById(R.id.like_num);
            mLikeNum = (TextView) itemView.findViewById(R.id.like_num);
            mIsLikeImage = (ImageView) itemView.findViewById(R.id.isLikeImage);
        }

        void bindWeibo(Weibo w) {
            mWeibo = w;
            mUserIcon.setImageURI(Uri.fromFile(new File(mWeibo.getWeiboUserIcon())));
            mUserNickname.setText(mWeibo.getWeiboUserNickname());
            mWeiboTime.setText(mWeibo.getWeiboTime());
            mWeiboContent.setText(mWeibo.getWeiboContent());
            mWeiboImg.setImageURI(Uri.fromFile(new File(mWeibo.getWeiboImg())));
            mForwardNum.setText(String.valueOf(mWeibo.getWeiboForwardNum()));
            mCommentNum.setText(String.valueOf(mWeibo.getWeiboCommentNum()));
            mLikeNum.setText(String.valueOf(mWeibo.getWeiboLikeNum()));
            if (w.isLike()) {
                mLikeNum.setTextColor(Color.parseColor("#ff8140"));
                mIsLikeImage.setImageResource(R.drawable.like2);
            }
        }

    }

    //其余的item的holder
    private class CommentHolder extends RecyclerView.ViewHolder {
        private Comment mComment;
        private ImageView mUserIcon;
        private TextView mUserNickname;
        private TextView mCommentContent;
        private TextView mCommentTime;
        private TextView mLikeNum;
        private ImageView mCommentIsLike;

        CommentHolder(View itemView) {
            super(itemView);
            mUserIcon = (ImageView) itemView.findViewById(R.id.user_icon);

            mUserNickname = (TextView) itemView.findViewById(R.id.user_nickname);
            mCommentContent = (TextView) itemView.findViewById(R.id.comment_content);
            mCommentTime = (TextView) itemView.findViewById(R.id.comment_time);
            mLikeNum = (TextView) itemView.findViewById(R.id.like_num);
            mCommentIsLike = (ImageView) itemView.findViewById(R.id.comment_is_like);
        }

        void bindComment(Comment c) {
            mComment = c;
            mUserIcon.setImageURI(Uri.fromFile(new File(mComment.getCommentUserIcon())));
            mUserNickname.setText(mComment.getCommentUserNickname());
            mCommentTime.setText(mComment.getCommentTime());
            mCommentContent.setText(mComment.getCommentContent());
            mLikeNum.setText(String.valueOf(mComment.getCommentLikeNum()));
            if (mComment.getCommentIsLike()) {
                mCommentIsLike.setImageResource(R.drawable.like2);
                mLikeNum.setTextColor(Color.parseColor("#ff8140"));
            }
            mCommentIsLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!mComment.getCommentIsLike()) {
                        mCommentIsLike.setImageResource(R.drawable.like2);
                        mLikeNum.setTextColor(Color.parseColor("#ff8140"));
                        //1=微博 2=评论  id
                        new LikeTask().execute(2, mComment.getCommentId());
                        InfoLab.getInstance().findCommentById(weiboId, mComment.getCommentId()).addCommentLikeNum();
                        InfoLab.getInstance().findCommentById(weiboId, mComment.getCommentId()).setCommentIsLike(true);
                    }
                }
            });

        }
    }

    private class WeiboAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private Weibo mWeibo;
        private int FIRST_ITEM = 0;
        private int OTHER_ITEM = 1;

        WeiboAdapter(Weibo w) {
            mWeibo = w;
        }

        //根据位置返回viewType
        @Override
        public int getItemViewType(int position) {
            return position == 0 ? FIRST_ITEM : OTHER_ITEM;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = null;
            if (viewType == FIRST_ITEM) {
                view = layoutInflater.inflate(R.layout.list_item_weibo, parent, false);
                return new WeiboHolder(view);
            } else if (viewType == OTHER_ITEM) {
                view = layoutInflater.inflate(R.layout.list_item_comment, parent, false);
                return new CommentHolder(view);
            } else {
                return null;
            }
        }


        //根据类型绑定数据到holder
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            List<Comment> commentList = mWeibo.getComments();

            if (holder instanceof WeiboHolder) {
                WeiboHolder weiboHolder = (WeiboHolder) holder;
                weiboHolder.bindWeibo(mWeibo);
            } else if (holder instanceof CommentHolder) {
                CommentHolder commentHolder = (CommentHolder) holder;
                commentHolder.bindComment(commentList.get(position - 1));
            }
        }

        @Override
        public int getItemCount() {
            return mWeibo.getComments().size() + 1;
        }
    }

}
