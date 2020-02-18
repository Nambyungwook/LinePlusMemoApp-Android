package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.list.ImgListAdapter;
import com.nbw.lineplusmemoapp.list.ImgListDecoration;
import com.nbw.lineplusmemoapp.list.ImgListItem;

import java.util.ArrayList;

public class MemoViewActivity extends AppCompatActivity {

    int id;
    String title;
    String content;
    ArrayList<String> imgArray;

    TextView tv_title_big;
    TextView tv_content_big;
    RecyclerView img_recycle_view_memo_view;
    ImgListAdapter imgListAdapter;

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
        img_recycle_view_memo_view = (RecyclerView) findViewById(R.id.img_recycle_view_memo_view);

        tv_title_big.setText(title);
        tv_content_big.setText(content);

        //setImgRecycleView(imgArray);

    }

    private void setImgRecycleView(ArrayList<String> imgArray) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        img_recycle_view_memo_view.setLayoutManager(layoutManager);

        imgListAdapter = new ImgListAdapter();

        imgListAdapter.setImgItemList(imgArray);

        img_recycle_view_memo_view.setAdapter(imgListAdapter);

        ImgListDecoration imgListDecoration = new ImgListDecoration();
        img_recycle_view_memo_view.addItemDecoration(imgListDecoration);
    }
}
