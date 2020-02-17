package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.nbw.lineplusmemoapp.R;

public class MemoSettingActivity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_content;
    private RecyclerView img_recycle_view_memo_setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_setting);

        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        img_recycle_view_memo_setting = (RecyclerView) findViewById(R.id.img_recycle_view_memo_setting);
    }

    public void onClickAddImg(View view) {
        //이미지 주소(url or 장치내 경로) sqlite디비에 저장, 리사이클뷰에 이미지 보여주기
    }

    public void onClickSaveMemo(View view) {
        //메모 데이터 sqlite디비에 저장
    }
}
