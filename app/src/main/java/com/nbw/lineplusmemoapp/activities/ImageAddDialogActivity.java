package com.nbw.lineplusmemoapp.activities;


import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.list.ImgListAdapter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ImageAddDialogActivity extends AppCompatActivity {

    EditText et_url;

    String strUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_add_dialog);

        //상태 바 제거(전체화면 모드)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        et_url = (EditText) findViewById(R.id.et_url);
    }


    public void onClickPhoto(View view) {
        takePhoto();
    }

    public void onClickGallery(View view) {
        gallery();
    }

    private void takePhoto() {
        //사진 촬영
    }

    private void gallery() {
        //갤러리
    }

    public void onClickURL(View view) {
        strUrl = et_url.getText().toString();
    }

    //확인
    public void onClickOK(View view) {
        //이미지 저장하기
    }

}
