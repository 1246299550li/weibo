package com.liheyu.weibo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.liheyu.weibo.net.InfoFetcher;
import com.liheyu.weibo.net.InfoLab;
import com.liheyu.weibo.R;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText mAccount;
    private EditText mPwd;
    private Button mLogin;
    private Button mRegister;

    @SuppressLint("StaticFieldLeak")
    private class LoginTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            InfoFetcher infoFetcher = new InfoFetcher();
            infoFetcher.login(params[0], params[1]);
            return null;
        }

        //线程结束刷线UI
        @Override
        protected void onPostExecute(Void aVoid) {

            if (InfoLab.getInstance().isLogin()) {
                Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginActivity.this, IndexActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, R.string.login_fail, Toast.LENGTH_LONG).show();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class RegisterTask extends AsyncTask<String, Void, Void> {
        private boolean isRegisterSuccess;

        @Override
        protected Void doInBackground(String... params) {
            InfoFetcher infoFetcher = new InfoFetcher();
            isRegisterSuccess = infoFetcher.register(params[0], params[1]);
            return null;
        }

        //线程结束刷线UI
        @Override
        protected void onPostExecute(Void aVoid) {
            if (isRegisterSuccess) {
                Toast.makeText(LoginActivity.this, R.string.register_success, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(LoginActivity.this, R.string.register_fail, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        mAccount = findViewById(R.id.account_input);
        mPwd = findViewById(R.id.password_input);

        mLogin = findViewById(R.id.btn_login);
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAccount.getText().toString().equals("") && !mPwd.getText().toString().equals(""))
                    new LoginTask().execute(mAccount.getText().toString(), mPwd.getText().toString());
            }
        });
        mRegister = findViewById(R.id.btn_register);
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mAccount.getText().toString().equals("") && !mPwd.getText().toString().equals(""))
                new RegisterTask().execute(mAccount.getText().toString(), mPwd.getText().toString());
            }
        });
    }

}
