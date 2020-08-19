package com.nbw.lineplusmemoapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

        //MainActivity에서 선택한 메모에 대한 정보 받아오기
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");
        imgArray = intent.getStringArrayListExtra("imgArray");

        //위에서 받아온 정보를 텍스트 뷰, 리사이클 뷰에 적용
        tv_title_big = (TextView) findViewById(R.id.tv_title_big);
        tv_content_big = (TextView) findViewById(R.id.tv_content_big);

        tv_title_big.setText(title);
        tv_content_big.setText(content);

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
                Intent intent = new Intent(MemoViewActivity.this, MemoEditActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("title", title);
                intent.putExtra("content", content);
                intent.putExtra("imgArray", imgArray);
                startActivity(intent);
                finish();

                break;

            case R.id.memo_delete :
                //현재 메모 삭제
                AlertDialog.Builder builder = new AlertDialog.Builder(MemoViewActivity.this);
                builder.setTitle("메모 삭제");
                builder.setMessage("메모를 삭제하시겠습니까?");
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //디비에서 메모 데이터 삭제
                    }
                });
                builder.setNegativeButton("아니요",null);
                builder.show();

                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return false;
    }

}
