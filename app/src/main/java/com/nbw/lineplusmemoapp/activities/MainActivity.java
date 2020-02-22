package com.nbw.lineplusmemoapp.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.list.ImgListAdapter;
import com.nbw.lineplusmemoapp.list.MemoListAdapter;
import com.nbw.lineplusmemoapp.list.MemoListItem;
import com.nbw.lineplusmemoapp.sqlite.DatabaseHelper;
import com.nbw.lineplusmemoapp.tables.ImageTable;
import com.nbw.lineplusmemoapp.tables.MemoTable;

import java.util.ArrayList;
import java.util.List;

import static com.nbw.lineplusmemoapp.tables.ImageTable.ImageEntry.IMG_TABLE_NAME;
import static com.nbw.lineplusmemoapp.tables.MemoTable.MemoEntry.MEMO_TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    public static Activity MainActivity;
    public static Cursor cursorMemoData;
    public static MemoListAdapter memoListAdapter;
    public static ImgListAdapter imgListAdapter;

    //메모 리스트뷰
    public static ListView memoListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //카메라, 앨범 에서 이미지를 가져오기 위한 권한설정 - TedPermission 라이브러리 사용(https://gun0912.tistory.com/61참고)
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "권한 허가", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "권한 거부\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }

        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("카메라, 앨범에서 이미지를 불러오기 위한 권한 설정이 필요합니다.\n[설정] > [권한]에서 변경하실 수 있습니다.")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();

        MainActivity = MainActivity.this;

        cursorMemoData = getMemoData();

        //layout xml 연결
        memoListView = (ListView) findViewById(R.id.memo_list_view);

        imgListAdapter = new ImgListAdapter();

        //메모리스트 어댑터 생성
        memoListAdapter = new MemoListAdapter(this, cursorMemoData);
        //메모리스트 어댑터 설정
        memoListView.setAdapter(memoListAdapter);

        //메모리스트뷰 클릭시 이벤트 설정
        memoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                final Cursor cursorImgData = getImgData();

                int idMemo = cursorMemoData.getInt(cursorMemoData.getColumnIndexOrThrow(MemoTable.MemoEntry._ID));
                String title = cursorMemoData.getString(cursorMemoData.getColumnIndexOrThrow(MemoTable.MemoEntry.COLUMN_NAME_TITLE));
                String content = cursorMemoData.getString(cursorMemoData.getColumnIndexOrThrow(MemoTable.MemoEntry.COLUMN_NAME_CONTENT));

                ArrayList<String> imgArray = new ArrayList<String>();
                while (cursorImgData.moveToNext()) {
                    if (cursorImgData.getInt(cursorImgData.getColumnIndexOrThrow(ImageTable.ImageEntry.COLUMN_NAME_MEMO_INDEX))==idMemo) {
                        String tmpImgStr = cursorImgData.getString(1);
                        imgArray.add(tmpImgStr);
                    }
                }


                Intent intent = new Intent(getApplicationContext(), MemoViewActivity.class);
                intent.putExtra("id", idMemo);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("imgArray", imgArray);
                startActivity(intent);
            }
        });
    }

    //DB에서 메모테이블 조회하는 메소드
    public Cursor getMemoData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.getReadableDatabase()
                .query(MEMO_TABLE_NAME,null,null,null, null, null, null);
    }

    //DB에서 이미지테이블 조회하는 메소드
    public Cursor getImgData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.getReadableDatabase()
                .query(IMG_TABLE_NAME,null,null,null, null, null, null);
    }


    public void onClickAddMemo(View view) {
        Intent intent = new Intent(MainActivity.this, MemoSettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        return;
    }
}
