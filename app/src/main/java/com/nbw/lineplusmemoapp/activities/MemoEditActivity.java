package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.list.ImgListAdapter;
import com.nbw.lineplusmemoapp.list.ImgListDecoration;
import com.nbw.lineplusmemoapp.sqlite.DatabaseHelper;
import com.nbw.lineplusmemoapp.tables.ImageTable;
import com.nbw.lineplusmemoapp.tables.MemoTable;

import java.util.ArrayList;
import java.util.Set;

import static com.nbw.lineplusmemoapp.activities.MainActivity.imgListAdapter;
import static com.nbw.lineplusmemoapp.tables.ImageTable.ImageEntry.IMG_TABLE_NAME;
import static com.nbw.lineplusmemoapp.tables.MemoTable.MemoEntry.MEMO_TABLE_NAME;

public class MemoEditActivity extends AppCompatActivity {

    int id;
    String title;
    String content;
    ArrayList<String> imgArray;

    private long memoId = -1;
    private long imgId = -1;

    EditText et_title_edit;
    EditText et_content_edit;
    RecyclerView img_recycle_view_memo_edit;
    Button btn_save_memo_edit;
    Button btn_save_all_edit;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);

        et_title_edit = (EditText) findViewById(R.id.et_title_edit);
        et_content_edit = (EditText) findViewById(R.id.et_content_edit);
        img_recycle_view_memo_edit = (RecyclerView) findViewById(R.id.img_recycle_view_memo_edit);
        btn_save_memo_edit = (Button) findViewById(R.id.btn_save_memo_edit);
        btn_save_all_edit = (Button) findViewById(R.id.btn_save_all_edit);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        imgArray = intent.getStringArrayListExtra("imgArray");

        et_title_edit.setText(title);
        et_content_edit.setText(content);

        setImgRecycleView(imgArray);
    }

    //이미지 주소, 경로가 들어간 arraylist를 입력으로 리사이클뷰에 이미지 보여주는 메소드
    private void setImgRecycleView(ArrayList<String> imgArray) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        img_recycle_view_memo_edit.setLayoutManager(layoutManager);

        imgListAdapter = new ImgListAdapter();

        imgListAdapter.setImgItemList(imgArray);

        img_recycle_view_memo_edit.setAdapter(imgListAdapter);

        ImgListDecoration imgListDecoration = new ImgListDecoration();
        img_recycle_view_memo_edit.addItemDecoration(imgListDecoration);
    }

    public void onClickAddImg(View view) {
        //기존에 이미지 주소 삭제
        if (imgArray != null) {
            imgArray.clear();
        }

        //이미지 추가 다이얼로그로 연결
        Intent intent = new Intent(this, ImageAddDialogActivity.class);
        startActivity(intent);
    }

    public void onClickSaveMemo(View view) {
        //메모 데이터 sqlite디비에 저장
        //제목과 본문을 저장
        String title = et_title_edit.getText().toString();
        String content = et_content_edit.getText().toString();

        ContentValues contentValues_memo = new ContentValues();
        contentValues_memo.put(MemoTable.MemoEntry.COLUMN_NAME_TITLE, title);
        contentValues_memo.put(MemoTable.MemoEntry.COLUMN_NAME_CONTENT, content);


        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        if (memoId == -1) {
            long newRowID = db.insert(MEMO_TABLE_NAME, null, contentValues_memo);

            if (newRowID == -1) {
                Toast.makeText(this, "save error", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
            }
        }
        btn_save_memo_edit.setVisibility(View.GONE);
        btn_save_all_edit.setVisibility(View.VISIBLE);

    }

    public void onClickSaveALL(View view) {
        oldDataDelete();

        databaseHelper = new DatabaseHelper(MainActivity.MainActivity);
        SQLiteDatabase dbRead = databaseHelper.getReadableDatabase();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        Cursor cursor;
        cursor = dbRead.rawQuery("SELECT "+ MemoTable.MemoEntry._ID+", "+ MemoTable.MemoEntry.COLUMN_NAME_TITLE +" FROM " + MEMO_TABLE_NAME, null);
        cursor.moveToLast();
        int memo_id = cursor.getInt(0);

        //가지고 있는 이미지 경로 or url 문자열이 있는지 확인
        if (imgArray!=null) {

            for (int i = 0 ; i <imgArray.size(); i ++) {
                ContentValues contentValues_img  = new ContentValues();
                contentValues_img.put(ImageTable.ImageEntry.COLUMN_NAME_IMG, imgArray.get(i));
                contentValues_img.put(ImageTable.ImageEntry.COLUMN_NAME_MEMO_INDEX, memo_id);

                if (imgId == -1) {
                    long newRowID = db.insert(IMG_TABLE_NAME, null, contentValues_img);

                    if (newRowID == -1) {
                        Toast.makeText(this, "save error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                    }
                }
            }

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();

            return;
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    //기존 디비 데이터 삭제 메소드
    private void oldDataDelete() {
        SQLiteDatabase db = new DatabaseHelper(MemoEditActivity.this).getWritableDatabase();
        int deleteMemoCount = db.delete(MemoTable.MemoEntry.MEMO_TABLE_NAME, MemoTable.MemoEntry._ID + "=" + id, null);
        int deleteImgCount = db.delete(IMG_TABLE_NAME, ImageTable.ImageEntry.COLUMN_NAME_MEMO_INDEX+"="+id, null);

        if (deleteMemoCount==0 && deleteImgCount==0) {
            Toast.makeText(MemoEditActivity.this, "메모수정에 오류가 생겼습니다.", Toast.LENGTH_SHORT).show();

        } else {

            MainActivity mainActivity = (MainActivity)MainActivity.MainActivity;
            mainActivity.finish();

            startActivity(new Intent(MemoEditActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("LinePlusMemoApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int strImgChecker = sharedPreferences.getInt("strImgChecker", 0);
        if (strImgChecker ==1) {

            Set<String> set = sharedPreferences.getStringSet("imgArray", null);
            imgArray = new ArrayList<String>(set);
            editor.putInt("strImgChecker",0);
            editor.commit();

            setImgRecycleView(imgArray);
        }

    }
}
//https://kr.seaicons.com/wp-content/uploads/2015/08/sun-icon.png
//https://png.pngtree.com/element_pic/00/16/08/2857c1bc4890f3e.jpg