package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.list.MemoListAdapter;
import com.nbw.lineplusmemoapp.list.MemoListItem;
import com.nbw.lineplusmemoapp.sqlite.DatabaseHelper;
import com.nbw.lineplusmemoapp.tables.MemoTable;

import java.util.ArrayList;

import static com.nbw.lineplusmemoapp.tables.MemoTable.MemoEntry.MEMO_TABLE_NAME;

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
        //this.setTestMemoList();

        final Cursor cursor = getMemoData();

        //layout xml 연결
        memoListView = (ListView) findViewById(R.id.memo_list_view);

        //메모리스트 어댑터 생성
        MemoListAdapter memoListAdapter = new MemoListAdapter(this, cursor);
        //메모리스트 어댑터 설정
        memoListView.setAdapter(memoListAdapter);

        //메모리스트뷰 클릭시 이벤트 설정
        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MemoTable.MemoEntry._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MemoTable.MemoEntry.COLUMN_NAME_TITLE));
                String content = cursor.getString(cursor.getColumnIndexOrThrow(MemoTable.MemoEntry.COLUMN_NAME_CONTENT));
                //ArrayList<String> imgArray = memoListAdapter.getItem(position).getImgArray();

                Intent intent = new Intent(getApplicationContext(), MemoViewActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                //intent.putExtra("imgArray", imgArray);
                startActivity(intent);
            }
        });
    }

    //DB에서 메모테이블 조회하는 메소드
    private Cursor getMemoData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.getReadableDatabase()
                .query(MEMO_TABLE_NAME,null,null,null, null, null, null);
    }

//    //DB에서 이미지테이블 조회하는 메소드
//    private Cursor getImgData() {
//        DatabaseHelper databaseHelper = new DatabaseHelper(this);
//        return databaseHelper.getReadableDatabase()
//                .query(IMG_TABLE_NAME,null,null,null, null, null, null);
//    }


    public void onClickAddMemo(View view) {
        Intent intent = new Intent(MainActivity.this, MemoSettingActivity.class);
        startActivity(intent);
    }
}
