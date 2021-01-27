package com.liheyu.weibo.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liheyu.weibo.net.InfoFetcher;
import com.liheyu.weibo.net.InfoLab;
import com.liheyu.weibo.R;
import com.liheyu.weibo.bean.Weibo;
import com.liheyu.weibo.activity.WriteActivity;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.io.File;
import java.util.List;
import java.util.Objects;


public class IndexFragment extends Fragment {

    private IndexListener mListener;

    private WeiboAdapter mAdapter;

    private RecyclerView mWeatherRecyclerView;

    private TextView textView;

    public IndexFragment() {

    }


    public static IndexFragment newInstance() {
        IndexFragment fragment = new IndexFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        fragment.setArguments(args);
        return fragment;
    }

    private class FetchDataTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            InfoFetcher infoFetcher = new InfoFetcher();
            infoFetcher.getWeiboList();
            Log.e("print", "success 有数据：" + InfoLab.getInstance().getWeiboList().size());
            return null;
        }

        //线程结束刷线UI
        @Override
        protected void onPostExecute(Void aVoid) {
            updateUI();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        new FetchDataTask().execute();
        View view = inflater.inflate(R.layout.fragment_index, container, false);

        mWeatherRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mWeatherRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        textView = Objects.requireNonNull(getActivity()).findViewById(R.id.title);

        //刷新时开启子线程
        RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                new FetchDataTask().execute();
                refreshlayout.finishRefresh(900);
            }
        });
        updateUI();
        return view;
    }

    //设置菜单方法
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = WriteActivity.newIntent(getContext(), -1);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //更新UI，没有adapter则新建
    private void updateUI() {
        List<Weibo> weiboList = InfoLab.getInstance().getWeiboList();
        mAdapter = new WeiboAdapter(weiboList);
        mWeatherRecyclerView.setAdapter(mAdapter);
    }

    //holder
    private class WeiboHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
        private TextView mForward;
        private LinearLayout mBg;


        public WeiboHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mUserIcon = (ImageView) itemView.findViewById(R.id.user_icon);
            mUserNickname = (TextView) itemView.findViewById(R.id.user_nickname);
            mWeiboTime = (TextView) itemView.findViewById(R.id.weibo_time);
            mWeiboContent = (TextView) itemView.findViewById(R.id.weibo_content);
            mWeiboImg = (ImageView) itemView.findViewById(R.id.weibo_img);
            mForwardNum = (TextView) itemView.findViewById(R.id.forward_num);
            mCommentNum = (TextView) itemView.findViewById(R.id.comment_num);
            mLikeNum = (TextView) itemView.findViewById(R.id.like_num);
            mIsLikeImage = (ImageView) itemView.findViewById(R.id.isLikeImage);
            mForward = (TextView) itemView.findViewById(R.id.forward);
            mBg = (LinearLayout) itemView.findViewById(R.id.bg);
        }


        @SuppressLint("SetTextI18n")
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
            if (mWeibo.getWeiboType() == 2) {
                mForward.setText("转发语：" + mWeibo.getWeiboForwardComment());
                final ViewGroup.LayoutParams lp = mForward.getLayoutParams();
                lp.height = 70;//lp.height=LayoutParams.WRAP_CONTENT;
                mForward.setLayoutParams(lp);
                Log.e("print", "zhufa");
                mBg.setBackgroundColor(Color.parseColor("#e0e0e0"));

            }

            if (w.isLike()) {
                mLikeNum.setTextColor(Color.parseColor("#ff8140"));
                mIsLikeImage.setImageResource(R.drawable.like2);
            }
        }

        @Override
        public void onClick(View v) {
            mListener.onWeiboSelected(mWeibo);
        }
    }

    private class WeiboAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private List<Weibo> mWeiboList;

        WeiboAdapter(List<Weibo> weiboList) {
            mWeiboList = weiboList;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new WeiboHolder(layoutInflater.inflate(R.layout.list_item_weibo, parent, false));

        }

        //绑定数据到holder
        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Weibo w = mWeiboList.get(position);
            WeiboHolder weiboHolder = (WeiboHolder) holder;
            weiboHolder.bindWeibo(w);
        }

        @Override
        public int getItemCount() {
            return mWeiboList.size();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        textView.setText("热门");
//        updateUI();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (IndexListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface IndexListener {
        void onWeiboSelected(Weibo w);
    }
}
