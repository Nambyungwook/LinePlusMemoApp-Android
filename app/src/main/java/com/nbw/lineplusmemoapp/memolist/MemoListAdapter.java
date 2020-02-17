package com.nbw.lineplusmemoapp.memolist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nbw.lineplusmemoapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

//메로 리스트 어댑터
public class MemoListAdapter extends BaseAdapter {

    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<MemoListItem> memoListItems;

    // url주소에서 이미지 설정시 사용하기위한 bitmap
    Bitmap bitmap;

    public MemoListAdapter(Context context, ArrayList<MemoListItem> ListItems) {
        mContext = context;
        memoListItems = ListItems;

        mLayoutInflater = LayoutInflater.from(mContext);
    }

    //리스트 크기
    @Override
    public int getCount() {
        return memoListItems.size();
    }

    //리스트 아이템 가져오기
    @Override
    public MemoListItem getItem(int position) {
        return memoListItems.get(position);
    }

    //리스트 아이템 id
    @Override
    public long getItemId(int position) {
        return memoListItems.get(position).getId();
    }

    //메로 리스트뷰 화면 설정
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (mLayoutInflater == null)
            {
                mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = mLayoutInflater.inflate(R.layout.memo_list_item, parent, false);
        }

        ImageView ivSmall = (ImageView)convertView.findViewById(R.id.img_small);
        TextView tvTitle = (TextView)convertView.findViewById(R.id.tv_title_small);
        TextView tvContent = (TextView)convertView.findViewById(R.id.tv_content_small);

        //미리보기 이미지 주소 - url 혹은 스마트폰 내부 저장소 경로
        final String previewImg = memoListItems.get(position).getImgArray().get(0);

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
        tvTitle.setText(memoListItems.get(position).getTitle());
        tvContent.setText(memoListItems.get(position).getContent());

        return convertView;
    }
}
