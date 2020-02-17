package com.nbw.lineplusmemoapp.list;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.nbw.lineplusmemoapp.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ImgListAdapter extends RecyclerView.Adapter<ImgListAdapter.ViewHolder> {

    private ArrayList<String> imgItemList;

    Bitmap bitmap;

    public void setImgItemList(ArrayList<String> imgList) {
        imgItemList = imgList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 사용할 아이템의 뷰를 생성해준다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_item, parent, false);

        ImgListAdapter.ViewHolder holder = new ImgListAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //미리보기 이미지 주소 - url 혹은 스마트폰 내부 저장소 경로
        final String previewImg = imgItemList.get(position);

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

            holder.iv_item_img.setImageBitmap(bitmap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return imgItemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_item_img;

        public ViewHolder(View itemView) {
            super(itemView);

            iv_item_img = itemView.findViewById(R.id.iv_item_img);
        }
    }
}
