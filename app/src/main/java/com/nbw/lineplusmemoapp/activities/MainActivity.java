package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.memolist.MemoListAdapter;
import com.nbw.lineplusmemoapp.memolist.MemoListItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //메모 리스트뷰
    ListView memoListView;
    //메모 리스트아이템들
    ArrayList<MemoListItem> memoListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //test용 메모리스트 설정
        this.setTestMemoList();

        //layout xml 연결
        memoListView = (ListView) findViewById(R.id.memo_list_view);

        //메모리스트 어댑터 생성
        final MemoListAdapter memoListAdapter = new MemoListAdapter(this, memoListItems);
        //메모리스트 어댑터 설정
        memoListView.setAdapter(memoListAdapter);

        //메모리스트뷰 클릭시 이벤트 설정
        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                int id = memoListAdapter.getItem(position).getId();
                String title = memoListAdapter.getItem(position).getTitle();
                String content = memoListAdapter.getItem(position).getContent();
                ArrayList<String> imgArray = memoListAdapter.getItem(position).getImgArray();

                Intent intent = new Intent(getApplicationContext(), MemoViewActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("imgArray", imgArray);
                startActivity(intent);
            }
        });


    }

    //test용 메모리스트 설정
    public void setTestMemoList() {
        memoListItems = new ArrayList<MemoListItem>();
        ArrayList<String> tmpImgArray = new ArrayList<String>();
        tmpImgArray.add("https://kr.seaicons.com/wp-content/uploads/2015/10/Sun-icon3.png");


        memoListItems.add(new MemoListItem(1, "해1", "해 사진입니당~~1",tmpImgArray));
        memoListItems.add(new MemoListItem(2, "해2", "해 사진입니당~~2",tmpImgArray));
        memoListItems.add(new MemoListItem(3, "해3", "해 사진입니당~~3",tmpImgArray));
    }
}
