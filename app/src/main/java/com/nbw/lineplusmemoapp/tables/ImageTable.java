package com.nbw.lineplusmemoapp.tables;

import android.provider.BaseColumns;

//이미지 테이블
public class ImageTable {

    private ImageTable() {

    }

    public static class ImageEntry implements BaseColumns {
        public static final String IMG_TABLE_NAME = "img";
        public static final String COLUMN_NAME_MEMO_INDEX = "memo_id";
        public static final String COLUMN_NAME_IMG = "img_url";
    }
}
