package com.liheyu.weibo.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liheyu.weibo.ui.EditDialog;
import com.liheyu.weibo.net.InfoFetcher;
import com.liheyu.weibo.net.InfoLab;
import com.liheyu.weibo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;


public class MyFragment extends Fragment {
    private ImageView mUserIcon;
    private TextView mUserNickname;
    private ImageView mUserSex;
    private Button mButton;
    private TextView mUserIntro;
    private TextView textView;
    private String path;


    public static MyFragment newInstance() {
        return new MyFragment();
    }

    @SuppressLint("StaticFieldLeak")
    private class UpdateTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            InfoFetcher infoFetcher = new InfoFetcher();
            infoFetcher.update(params[0], params[1], params[2], params[3]);
            return null;
        }

        //线程结束刷线UI
        @Override
        protected void onPostExecute(Void aVoid) {
            updateUI();
            Toast.makeText(getContext(), R.string.update_success, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //解决权限问题
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        textView = Objects.requireNonNull(getActivity()).findViewById(R.id.title);
        mUserIcon = view.findViewById(R.id.user_icon);
        mUserIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypeDialog();
            }
        });
        mUserIntro = view.findViewById(R.id.user_intro);
        mUserNickname = view.findViewById(R.id.user_nickname);
        mUserSex = view.findViewById(R.id.user_sex);
        mButton = view.findViewById(R.id.update);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditDialog dialog = new EditDialog(getContext());
                dialog.setTitle("修改信息")
                        .setNickname(InfoLab.getInstance().getUserNickname())
                        .setSex(InfoLab.getInstance().getUserSex() + "")
                        .setIntro(InfoLab.getInstance().getUserIntro())
                        .setSingle(false).setOnClickBottomListener(new EditDialog.OnClickBottomListener() {
                    @Override
                    public void onPositiveClick(String nickname, String sex, String intro) {
                        InfoLab.getInstance().setUserNickname(nickname);
                        InfoLab.getInstance().setUserSex(Integer.valueOf(sex));
                        InfoLab.getInstance().setUserIntro(intro);
                        dialog.dismiss();
                        new UpdateTask().execute(InfoLab.getInstance().getUserIcon(), nickname, sex, intro);
                    }

                    @Override
                    public void onNegtiveClick() {
                        dialog.dismiss();
                    }
                }).show();

            }
        });
        updateUI();
        return view;
    }

    @SuppressLint("SetTextI18n")
    public void updateUI() {
        mUserIcon.setImageBitmap(BitmapFactory.decodeFile(InfoLab.getInstance().getUserIcon()));
        mUserNickname.setText(InfoLab.getInstance().getUserNickname());
        mUserSex.setImageResource(InfoLab.getInstance().getUserSex() == 1 ? R.drawable.man : R.drawable.woman);
        mUserIntro.setText("介绍:" + InfoLab.getInstance().getUserIntro());
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.findItem(R.id.action_add).setVisible(false);
        textView.setText("我的信息");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        textView.setText("我的信息");
        super.onResume();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void showTypeDialog() {
        //显示对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final AlertDialog dialog = builder.create();
        View view = View.inflate(getContext(), R.layout.dialog_select_photo, null);
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
                        Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "IMG_" + "user_temp" + ".jpg")));
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
                InfoLab.getInstance().setUserIcon(path);
                mUserIcon.setImageBitmap(BitmapFactory.decodeFile(path));
                new UpdateTask().execute(path, InfoLab.getInstance().getUserNickname(), InfoLab.getInstance().getUserSex() + "", InfoLab.getInstance().getUserIntro());
                break;
            case 2:
                path = Environment.getExternalStorageDirectory() + "/IMG_" + "user_temp" + ".jpg";
                qualityCompress(path);
                InfoLab.getInstance().setUserIcon(path);
                mUserIcon.setImageBitmap(BitmapFactory.decodeFile(path));
                new UpdateTask().execute(path, InfoLab.getInstance().getUserNickname(), InfoLab.getInstance().getUserSex() + "", InfoLab.getInstance().getUserIntro());
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
            Cursor cursor = getActivity().getContentResolver().query(uri,
                    new String[]{MediaStore.Images.Media.DATA}, null, null, null);
            if (null == cursor) {
                Toast.makeText(getContext(), "图片没找到", Toast.LENGTH_SHORT).show();
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
