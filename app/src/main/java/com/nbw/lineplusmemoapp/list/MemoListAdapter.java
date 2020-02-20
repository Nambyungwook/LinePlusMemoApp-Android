package com.nbw.lineplusmemoapp.list;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nbw.lineplusmemoapp.R;
import com.nbw.lineplusmemoapp.activities.MainActivity;
import com.nbw.lineplusmemoapp.sqlite.DatabaseHelper;
import com.nbw.lineplusmemoapp.tables.ImageTable;
import com.nbw.lineplusmemoapp.tables.MemoTable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.nbw.lineplusmemoapp.activities.MainActivity.contextMain;
import static com.nbw.lineplusmemoapp.tables.ImageTable.ImageEntry.IMG_TABLE_NAME;

//메로 리스트 어댑터
public class MemoListAdapter extends CursorAdapter {

    int memoId;

    // url주소에서 이미지 설정시 사용하기위한 bitmap
    Bitmap bitmap;

    public MemoListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.memo_list_item, parent,false);
    }

    @Override
    public void bindView(View convertView, Context context, Cursor cursor) {
        ImageView ivSmall = (ImageView)convertView.findViewById(R.id.img_small);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tv_title_small);
        TextView tvContent = (TextView)convertView.findViewById(R.id.tv_content_small);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(MemoTable.MemoEntry.COLUMN_NAME_TITLE));
        String content = cursor.getString(cursor.getColumnIndexOrThrow(MemoTable.MemoEntry.COLUMN_NAME_CONTENT));
        memoId = cursor.getInt(cursor.getColumnIndexOrThrow(MemoTable.MemoEntry._ID));

        tvTitle.setText(title);
        tvContent.setText(content);

        Cursor cursorImg = getImgData();
        ArrayList<String> imgArray = new ArrayList<String>();
        while (cursorImg.moveToNext()) {
            if (cursorImg.getInt(cursorImg.getColumnIndexOrThrow(ImageTable.ImageEntry.COLUMN_NAME_MEMO_INDEX))==memoId) {
                String tmpImgStr = cursorImg.getString(1);
                imgArray.add(tmpImgStr);
            }
        }

        if (imgArray.size()!=0) {
            //미리보기 이미지 주소 - url 혹은 스마트폰 내부 저장소 경로
            final String previewImg = imgArray.get(0);

            //url일 경우 bitmap 변환 후 이미지뷰에 설정
            Thread mTread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL imgUrl = new URL(previewImg);

                        HttpURLConnection conn = (HttpURLConnection) imgUrl.openConnection();
                        conn.setDoInput(true);
                        conn.connect();

                        InputStream is = conn.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };

            mTread.start();

            try {
                mTread.join();

                ivSmall.setImageBitmap(bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            ivSmall.setImageResource(R.drawable.ic_launcher_background);
        }

    }

    //DB에서 이미지테이블 조회하는 메소드
    private Cursor getImgData() {
        DatabaseHelper databaseHelper = new DatabaseHelper(contextMain);

        return databaseHelper.getReadableDatabase()
                .query(IMG_TABLE_NAME,null,null,null, null, null, null);
    }
}
