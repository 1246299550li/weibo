package com.liheyu.weibo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.liheyu.weibo.ui.ChangeColorIconWithText;
import com.liheyu.weibo.fragment.IndexFragment;
import com.liheyu.weibo.fragment.MyFragment;
import com.liheyu.weibo.fragment.NoticeFragment;
import com.liheyu.weibo.R;
import com.liheyu.weibo.bean.Weibo;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class IndexActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener, View.OnClickListener, IndexFragment.IndexListener {


    private Toolbar mToolBar;
    private ViewPager mViewPager;
    private List<Fragment> mTabs = new ArrayList<Fragment>();

    private FragmentPagerAdapter mAdapter;

    private List<ChangeColorIconWithText> mTabIndicators = new ArrayList<ChangeColorIconWithText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
        initToolBar();
        initData();
    }


    private void initView() {
        mToolBar = findViewById(R.id.tool_bar);
        mViewPager = findViewById(R.id.view_pager);
        ChangeColorIconWithText index = (ChangeColorIconWithText) findViewById(R.id.id_index);
        mTabIndicators.add(index);
        ChangeColorIconWithText notice = (ChangeColorIconWithText) findViewById(R.id.id_notice);
        mTabIndicators.add(notice);
        ChangeColorIconWithText my = (ChangeColorIconWithText) findViewById(R.id.id_my);
        mTabIndicators.add(my);

        index.setOnClickListener(this);
        notice.setOnClickListener(this);
        my.setOnClickListener(this);

        index.setIconAlpha(1.0f);
        mViewPager.addOnPageChangeListener(this);
    }

    private void initToolBar() {
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            getSupportActionBar().setTitle("");
        }
    }

    private void initData() {

        mTabs.add(IndexFragment.newInstance());
        mTabs.add(NoticeFragment.newInstance());
        mTabs.add(MyFragment.newInstance());

        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mTabs.size();
            }

            @NonNull
            @Override
            public Fragment getItem(int position) {
                return mTabs.get(position);
            }
        };
        mViewPager.setAdapter(mAdapter);
    }


    /**
     * 利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置mOptionalIconsVisible为true，
     * 给菜单设置图标时才可见 让菜单同时显示图标和文字
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * 如果是直接点击图标来跳转页面的话，position值为0到3，positionOffset一直为0.0
     * 如果是通过滑动来跳转页面的话
     * 假如是从第一页滑动到第二页
     * 在这个过程中，positionOffset从接近0逐渐增大到接近1.0，滑动完成后又恢复到0.0，而position只有在滑动完成后才从0变为1
     * 假如是从第二页滑动到第一页
     * 在这个过程中，positionOffset从接近1.0逐渐减小到0.0，而position一直是0
     *
     * @param position             当前页面索引
     * @param positionOffset       偏移量
     * @param positionOffsetPixels 偏移量
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset > 0) {
            ChangeColorIconWithText left = mTabIndicators.get(position);
            ChangeColorIconWithText right = mTabIndicators.get(position + 1);
            left.setIconAlpha(1 - positionOffset);
            right.setIconAlpha(positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    @Override
    public void onClick(View v) {
        resetTabsStatus();
        switch (v.getId()) {
            case R.id.id_index:
                mTabIndicators.get(0).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.id_notice:
                mTabIndicators.get(1).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(1, false);
                break;
            case R.id.id_my:
                mTabIndicators.get(2).setIconAlpha(1.0f);
                mViewPager.setCurrentItem(2, false);
                break;
            default:
        }
    }

    /**
     * 重置Tab状态
     */
    private void resetTabsStatus() {
        for (int i = 0; i < mTabIndicators.size(); i++) {
            mTabIndicators.get(i).setIconAlpha(0);
        }
    }

    @Override
    public void onWeiboSelected(Weibo w) {
        Intent intent = DetailActivity.newIntent(this, w.getWeiboId());
        startActivity(intent);
    }
}