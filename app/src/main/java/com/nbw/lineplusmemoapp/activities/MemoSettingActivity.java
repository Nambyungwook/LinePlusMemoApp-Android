package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.sqlite.DatabaseHelper;
import com.nbw.lineplusmemoapp.tables.MemoTable;

import static com.nbw.lineplusmemoapp.tables.MemoTable.MemoEntry.MEMO_TABLE_NAME;

public class MemoSettingActivity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_content;
    private RecyclerView img_recycle_view_memo_setting;

    private long memoId = -1;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_setting);

        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        img_recycle_view_memo_setting = (RecyclerView) findViewById(R.id.img_recycle_view_memo_setting);
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

        ContentValues contentValues = new ContentValues();
        contentValues.put(MemoTable.MemoEntry.COLUMN_NAME_TITLE, title);
        contentValues.put(MemoTable.MemoEntry.COLUMN_NAME_CONTENT, content);

        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        if (memoId == -1) {
            long newRowID = db.insert(MEMO_TABLE_NAME, null, contentValues);

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
}
