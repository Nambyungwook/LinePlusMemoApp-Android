package com.nbw.lineplusmemoapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        //MainActivity에서 선택한 메모에 대한 정보 받아오기
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        imgArray = intent.getStringArrayListExtra("imgArray");

        //위에서 받아온 정보를 텍스트 뷰, 리사이클 뷰에 적용
        tv_title_big = (TextView) findViewById(R.id.tv_title_big);
        tv_content_big = (TextView) findViewById(R.id.tv_content_big);
        img_recycle_view_memo_view = (RecyclerView) findViewById(R.id.img_recycle_view_memo_view);

        tv_title_big.setText(title);
        tv_content_big.setText(content);

        //이미지 주소, 경로가 들어간 arraylist를 입력으로 리사이클뷰에 이미지 보여주는 메소드
        setImgRecycleView(imgArray);

    }

    //이미지 주소, 경로가 들어간 arraylist를 입력으로 리사이클뷰에 이미지 보여주는 메소드
    private void setImgRecycleView(ArrayList<String> imgArray) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        img_recycle_view_memo_view.setLayoutManager(layoutManager);

        imgListAdapter = new ImgListAdapter();

        imgListAdapter.setImgItemList(imgArray);

        img_recycle_view_memo_view.setAdapter(imgListAdapter);

        ImgListDecoration imgListDecoration = new ImgListDecoration();
        img_recycle_view_memo_view.addItemDecoration(imgListDecoration);
    }

    //메뉴 연결
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    //메뉴 아이템 클릭시 이벤트
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.memo_update :
                //메모 내용 수정

            case R.id.memo_delete :
                //현재 메모 삭제

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
