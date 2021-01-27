package com.liheyu.weibo.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liheyu.weibo.net.InfoFetcher;
import com.liheyu.weibo.net.InfoLab;
import com.liheyu.weibo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;


public class WriteActivity extends AppCompatActivity {
    private static final String EXTRA_WEATHER_ID = "com.liheyu.weibo.type";
    private EditText mEditText;
    private ImageView mImageView;
    private TextView mForwardTitle;
    private TextView mForwardContent;
    private String path;
    private int weiboId;

    public static Intent newIntent(Context packageContext, int weiboId) {
        Intent intent = new Intent(packageContext, WriteActivity.class);
        intent.putExtra(EXTRA_WEATHER_ID, weiboId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        weiboId = getIntent().getIntExtra(EXTRA_WEATHER_ID, -1);
        mEditText = findViewById(R.id.write_text);
        mImageView = findViewById(R.id.add_pic);
        mForwardTitle = findViewById(R.id.title_forward);
        mForwardContent = findViewById(R.id.content_forward);
        if (weiboId == -1) {
            //写微博
            mForwardTitle.setText("");
            mForwardContent.setText("");
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showTypeDialog();
                }
            });
        } else {
            //转发微博
            LinearLayout bg = findViewById(R.id.bg);
            bg.setBackgroundColor(Color.parseColor("#e0e0e0"));
            mImageView.setImageURI(Uri.fromFile(new File(InfoLab.getInstance().findWeiboById(weiboId).getWeiboImg())));
            mForwardTitle.setText(InfoLab.getInstance().findWeiboById(weiboId).getWeiboUserNickname());
            mForwardContent.setText(InfoLab.getInstance().findWeiboById(weiboId).getWeiboContent());
        }
        Toolbar toolbar = findViewById(R.id.write_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //解决权限问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }

    @SuppressLint("StaticFieldLeak")
    private class AddTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            InfoFetcher infoFetcher = new InfoFetcher();
            infoFetcher.writeWeibo(params[0], params[1], params[2]);
            return null;
        }

        //线程结束刷线UI
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(WriteActivity.this, R.string.writeSuccess, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ForwardTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            InfoFetcher infoFetcher = new InfoFetcher();
            infoFetcher.forwardWeibo(params[0], params[1], params[2]);
            return null;
        }

        //线程结束刷线UI
        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(WriteActivity.this, R.string.writeSuccess, Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_write, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //设置菜单方法
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_write) {
            if (weiboId == -1) {
                new AddTask().execute(mEditText.getText().toString(), path, InfoLab.getInstance().getUserDevice());
//                Log.e("print", "");
            } else {
                new ForwardTask().execute(mEditText.getText().toString(), String.valueOf(weiboId), InfoLab.getInstance().getUserDevice());
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_select_photo, null);
        TextView tv_select_gallery = (TextView) view.findViewById(R.id.tv_select_gallery);
        TextView tv_select_camera = (TextView) view.findViewById(R.id.tv_select_camera);
        // 在相册中选取
        tv_select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                //打开文件
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, 1);
                dialog.dismiss();
            }
        });
        // 调用照相机
        tv_select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "IMG_" + "weibo_temp" + ".jpg")));
                startActivityForResult(intent2, 2);// 采用ForResult打开
                dialog.dismiss();
            }
        });
        dialog.setView(view);
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case 1:
                Uri uri = data.getData();
                path = getPAth(uri);
                Log.e("print", path);
                qualityCompress(path);
                mImageView.setImageBitmap(BitmapFactory.decodeFile(path));
                break;
            case 2:
                path = Environment.getExternalStorageDirectory() + "/IMG_" + "weibo_temp" + ".jpg";
                qualityCompress(path);
                mImageView.setImageBitmap(BitmapFactory.decodeFile(path));
                break;
            default:
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void qualityCompress(String url) {
        File file = new File(url);
        Bitmap bmp = BitmapFactory.decodeFile(url);
        // 0-100 100为不压缩
        int quality = 1;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPAth(Uri uri) {
        String path = null;
        if (!TextUtils.isEmpty(uri.getAuthority())) {
            Cursor cursor = getContentResolver().query(uri,
                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (null == cursor) {
                Toast.makeText(this, "图片没找到", Toast.LENGTH_SHORT).show();
                return null;
            }
            cursor.moveToFirst();
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        } else {
            path = uri.getPath();
        }
        return path;
    }



}
