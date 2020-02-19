package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.list.ImgListAdapter;
import com.nbw.lineplusmemoapp.list.ImgListDecoration;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ImageAddDialogActivity extends AppCompatActivity {

    private int REQUEST_IMAGE_CAPTURE = 0;//사진촬영
    private int REQUEST_GALLERY = 1;//갤러리

    EditText et_url;
    RecyclerView img_recycle_view_img_dialog;

    Bitmap bitmap;

    String strUrl;
    ArrayList<String> imgArray = new ArrayList<String>();

    ImgListAdapter imgListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_add_dialog);

        //상태 바 제거(전체화면 모드)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_url = (EditText) findViewById(R.id.et_url);
        img_recycle_view_img_dialog = (RecyclerView) findViewById(R.id.img_recycle_view_img_dialog);
    }

    //사진 촬영
    public void onClickPhoto(View view) {
        takePhoto();
    }

    //갤러리
    public void onClickGallery(View view) {
        gallery();
    }

    //URL입력
    public void onClickURL(View view) {
        strUrl = et_url.getText().toString();

        if (strUrl.equals("")) {
            Toast.makeText(this, "url을 입력해 주세요.", Toast.LENGTH_SHORT).show();
        } else {
            imgArray.add(strUrl);
            setImgRecycleView(imgArray);
        }

    }

    //확인
    public void onClickOK(View view) {
        SharedPreferences sharedPreferences = getSharedPreferences("LinePlusMemoApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Set<String> set = new HashSet<String>();

        set.addAll(imgArray);

        editor.putStringSet("imgArray", set);
        editor.putInt("strImgChecker",1);
        editor.commit();

        finish();
    }

    //사진촬영 메소드
    private void takePhoto() {
        // 촬영 후 이미지 가져옴

    }

    //갤러리 접근 메소드
    private void gallery() {
        //앨범에서 사진 선택
    }

    private void setImgRecycleView(ArrayList<String> imgArray) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        img_recycle_view_img_dialog.setLayoutManager(layoutManager);

        imgListAdapter = new ImgListAdapter();

        imgListAdapter.setImgItemList(imgArray);

        img_recycle_view_img_dialog.setAdapter(imgListAdapter);

        ImgListDecoration imgListDecoration = new ImgListDecoration();
        img_recycle_view_img_dialog.addItemDecoration(imgListDecoration);
    }

}
