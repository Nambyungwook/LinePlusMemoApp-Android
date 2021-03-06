package com.nbw.lineplusmemoapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.list.ImgListAdapter;
import com.nbw.lineplusmemoapp.sqlite.DatabaseHelper;
import com.nbw.lineplusmemoapp.tables.MemoTable;

import java.util.ArrayList;
import java.util.Set;

import static com.nbw.lineplusmemoapp.tables.ImageTable.ImageEntry.IMG_TABLE_NAME;
import static com.nbw.lineplusmemoapp.tables.MemoTable.MemoEntry.MEMO_TABLE_NAME;

public class MemoSettingActivity extends AppCompatActivity {

    private EditText et_title;
    private EditText et_content;
    private Button btn_save_memo;

    private long memoId = -1;
    private long imgId = -1;

    //실제 사용할 이미지들
    private ArrayList<String> imgArray;
    //ImageAddDialogActivity에서 받아온 이미지 주소 값 앞에 번호가 있는 값들
    private ArrayList<String> receiveImgArray;

    DatabaseHelper databaseHelper;

    ImgListAdapter imgListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_setting);

        et_title = (EditText) findViewById(R.id.et_title);
        et_content = (EditText) findViewById(R.id.et_content);
        btn_save_memo = (Button) findViewById(R.id.btn_save_memo);

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
        btn_save_memo.setVisibility(View.GONE);

    }

    //액티비티 재시작시에 해야하는 일
    @Override
    protected void onResume() {
        super.onResume();

        //불러올수있는 이미지 데이터가 있는지 확인하여 데이터를 가져옴 sharedpreferences는 set을 사용하기 때문에 중복되는 이미지값을 받아오기위해 앞에 숫자를 추가
        SharedPreferences sharedPreferences = getSharedPreferences("LinePlusMemoApp", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int strImgChecker = sharedPreferences.getInt("strImgChecker", 0);
        if (strImgChecker ==1) {

            Set<String> set = sharedPreferences.getStringSet("sendImgArray", null);
            receiveImgArray = new ArrayList<String>(set);
            imgArray = new ArrayList<String>();
            //set에서 가져올때 순서가 반대이므로 바로 잡아준다.
            for (int i = receiveImgArray.size()-1; i >= 0; i--) {
                //추가된 이미지 만큼의 숫자가 있으므로 자릿수에 따라 다르게 문자열을 잘라준다. - 실제로 10000개이상의 이미지를 추가하는 일이 없을듯
                if (i<10) {
                    String tmpImgStr = receiveImgArray.get(i).substring(1);
                    imgArray.add(tmpImgStr);
                } else if (i<100) {
                    String tmpImgStr = receiveImgArray.get(i).substring(2);
                    imgArray.add(tmpImgStr);
                } else if (i<1000) {
                    String tmpImgStr = receiveImgArray.get(i).substring(3);
                    imgArray.add(tmpImgStr);
                } else if (i<10000) {
                    String tmpImgStr = receiveImgArray.get(i).substring(4);
                    imgArray.add(tmpImgStr);
                }

            }

            editor.putInt("strImgChecker",0);
            editor.commit();
        }

    }

}

//https://kr.seaicons.com/wp-content/uploads/2015/08/sun-icon.png
//https://png.pngtree.com/element_pic/00/16/08/2857c1bc4890f3e.jpg