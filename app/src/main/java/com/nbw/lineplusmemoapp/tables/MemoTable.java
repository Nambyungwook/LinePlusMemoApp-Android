package com.nbw.lineplusmemoapp.tables;

import android.provider.BaseColumns;

//메모 테이블
public class MemoTable {

    private MemoTable() {

    }

    public static class MemoEntry implements BaseColumns {
        public static final String MEMO_TABLE_NAME = "memo";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
