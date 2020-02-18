package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.nbw.lineplusmemoapp.R;

public class ImageAddDialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_add_dialog);

        //상태 바 제거(전체화면 모드)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    //사진 촬영
    public void onClickPhoto(View view) {
    }

    //갤러리
    public void onClickGallery(View view) {
    }

    //URL입력
    public void onClickURL(View view) {
    }
}
