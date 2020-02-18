package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
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
import com.nbw.lineplusmemoapp.sqlite.DatabaseHelper;
import com.nbw.lineplusmemoapp.tables.ImageTable;
import com.nbw.lineplusmemoapp.tables.MemoTable;

import static com.nbw.lineplusmemoapp.tables.ImageTable.ImageEntry.IMG_TABLE_NAME;
import static com.nbw.lineplusmemoapp.tables.MemoTable.MemoEntry.MEMO_TABLE_NAME;

public class MemoSettingActivity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_content;
    private RecyclerView img_recycle_view_memo_setting;
    private Button btn_save_memo;

    private long memoId = -1;
    private long imgId = -1;

    private String strImg = null;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_setting);

        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        img_recycle_view_memo_setting = (RecyclerView) findViewById(R.id.img_recycle_view_memo_setting);
        btn_save_memo = (Button) findViewById(R.id.btn_save_memo);

        btn_save_memo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //메모 데이터 sqlite디비에 저장
                //제목과 본문을 저장
                String title = et_title.getText().toString();
                String content = et_content.getText().toString();

                ContentValues contentValues_memo = new ContentValues();
                contentValues_memo.put(MemoTable.MemoEntry.COLUMN_NAME_TITLE, title);
                contentValues_memo.put(MemoTable.MemoEntry.COLUMN_NAME_CONTENT, content);

                databaseHelper = new DatabaseHelper(getApplicationContext());
                SQLiteDatabase db = databaseHelper.getWritableDatabase();

                if (memoId == -1) {
                    long newRowID = db.insert(MEMO_TABLE_NAME, null, contentValues_memo);

                    if (newRowID == -1) {
                        Toast.makeText(getApplicationContext(), "save error", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "save success", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                    }
                }
                final Cursor cursor = getMemoData();
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MemoTable.MemoEntry._ID));

                //가지고 있는 이미지 경로 or url 문자열이 있는지 확인
                if (strImg!=null) {

                    ContentValues contentValues_img  = new ContentValues();
                    contentValues_img.put(ImageTable.ImageEntry.COLUMN_NAME_IMG, strImg);
                    contentValues_img.put(ImageTable.ImageEntry.COLUMN_NAME_MEMO_INDEX, id);

                    if (imgId == -1) {
                        long newRowID = db.insert(IMG_TABLE_NAME, null, contentValues_img);

                        if (newRowID == -1) {
                            Toast.makeText(getApplicationContext(), "save error", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "save success", Toast.LENGTH_SHORT).show();
                            setResult(RESULT_OK);

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void onClickAddImg(View view) {
        //이미지 추가 다이얼로그로 연결
        Intent intent = new Intent(this, ImageAddDialogActivity.class);
        startActivity(intent);
    }

    public void onClickSaveMemo(View view) {
        //메모 데이터 sqlite디비에 저장
        //제목과 본문을 저장
        String title = et_title.getText().toString();
        String content = et_content.getText().toString();

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

        //가지고 있는 이미지 경로 or url 문자열이 있는지 확인
        if (strImg!=null) {
            final Cursor cursor = getMemoData();

            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MemoTable.MemoEntry._ID));

            ContentValues contentValues_img  = new ContentValues();
            contentValues_img.put(ImageTable.ImageEntry.COLUMN_NAME_IMG, strImg);
            contentValues_img.put(ImageTable.ImageEntry.COLUMN_NAME_MEMO_INDEX, id);

            if (imgId == -1) {
                long newRowID = db.insert(IMG_TABLE_NAME, null, contentValues_img);

                if (newRowID == -1) {
                    Toast.makeText(this, "save error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "save success", Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK);

                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences("LinePlusMemoApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int strImgChecker = sharedPreferences.getInt("strImgChecker", 0);
        if (strImgChecker ==1) {
            strImg = sharedPreferences.getString("strImg", "");
            editor.putInt("strImgChecker",0);
            editor.commit();
        }
    }


    //DB에서 메모테이블 조회하는 메소드
    private Cursor getMemoData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        return databaseHelper.getReadableDatabase()
                .query(MEMO_TABLE_NAME,null,null,null, null, null, null);
    }
}

//https://kr.seaicons.com/wp-content/uploads/2015/08/sun-icon.png