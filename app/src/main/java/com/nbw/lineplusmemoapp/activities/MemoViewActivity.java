package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.nbw.lineplusmemoapp.R;

import java.util.ArrayList;

public class MemoViewActivity extends AppCompatActivity {

    int id;
    String title;
    String content;
    ArrayList<String> imgArray;

    TextView tv_title_big;
    TextView tv_content_big;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_view);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        imgArray = intent.getStringArrayListExtra("imgArray");

        tv_title_big = (TextView) findViewById(R.id.tv_title_big);
        tv_content_big = (TextView) findViewById(R.id.tv_content_big);

        tv_title_big.setText(title);
        tv_content_big.setText(content);

    }
}
