package com.liheyu.weibo.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.RadioGroup;
import android.widget.TextView;

import com.liheyu.weibo.R;


public class EditDialog extends Dialog {

    /**
     * 显示的标题
     */
    private TextView titleTv;
    private EditText mNickname;
    private RadioGroup mSex;
    private EditText mIntro;


    /**
     * 确认和取消按钮
     */
    private Button negtiveBn, positiveBn;

    /**
     * 按钮之间的分割线
     */
    private View columnLineView;

    public EditDialog(Context context) {
        super(context, R.style.CustomDialog);
    }

    /**
     * 都是内容数据
     */
    private String title;
    private String positive, negtive;
    private String nickname;
    private String sex;
    private String intro;

    /**
     * 底部是否只有一个按钮
     */
    private boolean isSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);
        //初始化界面控件
        initView();
        //初始化界面数据
        refreshView();
        //初始化界面控件的事件
        initEvent();
    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        positiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onPositiveClick(mNickname.getText().toString(), sex, mIntro.getText().toString());
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听
        negtiveBn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickBottomListener != null) {
                    onClickBottomListener.onNegtiveClick();
                }
            }
        });
    }

    /**
     * 初始化界面控件的显示数据
     */
    private void refreshView() {

        mNickname.setText(nickname);
        mIntro.setText(intro);
        //如果用户自定了title和message
        if (!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
            titleTv.setVisibility(View.VISIBLE);
        } else {
            titleTv.setVisibility(View.GONE);
        }

        //如果设置按钮的文字
        if (!TextUtils.isEmpty(positive)) {
            positiveBn.setText(positive);
        } else {
            positiveBn.setText("确定");
        }
        if (!TextUtils.isEmpty(negtive)) {
            negtiveBn.setText(negtive);
        } else {
            negtiveBn.setText("取消");
        }

        mSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (group.getCheckedRadioButtonId()) {
                    case R.id.radio_man:
                        sex = "1";
                        break;
                    case R.id.radio_woman:
                        sex = "2";
                        break;
                    default:
                        break;
                }
            }
        });


        /**
         * 只显示一个按钮的时候隐藏取消按钮，回掉只执行确定的事件
         */
        if (isSingle) {
            columnLineView.setVisibility(View.GONE);
            negtiveBn.setVisibility(View.GONE);
        } else {
            negtiveBn.setVisibility(View.VISIBLE);
            columnLineView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
        refreshView();
    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        negtiveBn = (Button) findViewById(R.id.negtive);
        positiveBn = (Button) findViewById(R.id.positive);
        titleTv = (TextView) findViewById(R.id.title);
        mNickname = (EditText) findViewById(R.id.nickname);
        mIntro = (EditText) findViewById(R.id.intro);
        mSex = (RadioGroup) findViewById(R.id.sex_group);
        columnLineView = findViewById(R.id.column_line);
    }

    /**
     * 设置确定取消按钮的回调
     */
    public OnClickBottomListener onClickBottomListener;

    public EditDialog setOnClickBottomListener(OnClickBottomListener onClickBottomListener) {
        this.onClickBottomListener = onClickBottomListener;
        return this;
    }

    public interface OnClickBottomListener {
        /**
         * 点击确定按钮事件
         */
        public void onPositiveClick(String nickname, String sex, String intro);

        /**
         * 点击取消按钮事件
         */
        public void onNegtiveClick();
    }


    public String getTitle() {
        return title;
    }

    public EditDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPositive() {
        return positive;
    }

    public EditDialog setPositive(String positive) {
        this.positive = positive;
        return this;
    }

    public String getNegtive() {
        return negtive;
    }

    public EditDialog setNegtive(String negtive) {
        this.negtive = negtive;
        return this;
    }


    public boolean isSingle() {
        return isSingle;
    }

    public EditDialog setSingle(boolean single) {
        isSingle = single;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public EditDialog setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getSex() {
        return sex;
    }

    public EditDialog setSex(String sex) {
        this.sex = sex;
        return this;
    }

    public String getIntro() {
        return intro;
    }

    public EditDialog setIntro(String intro) {
        this.intro = intro;
        return this;
    }
}