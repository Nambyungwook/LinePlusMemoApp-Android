package com.nbw.lineplusmemoapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nbw.lineplusmemoapp.tables.MemoTable;

//sqlite사용
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Lineplusmemo.db";
    public static final int DB_VERSION = 1;
    public static final String CREATE_MEMO_TABLE =
            String.format(
                    "CREATE TABLE %s (%s INTEGER PRIMERY KEY AUTOINCREMENT, %s TEXT, %s TEXT)",
                    MemoTable.MemoEntry.MEMO_TABLE_NAME,
                    MemoTable.MemoEntry._ID,
                    MemoTable.MemoEntry.COLUMN_NAME_TITLE,
                    MemoTable.MemoEntry.COLUMN_NAME_CONTENT);

    public static final String DELETE_MEMO_TABLE = "DROP TABLE IF EXISTS " + MemoTable.MemoEntry.MEMO_TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MEMO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(DELETE_MEMO_TABLE);
        onCreate(sqLiteDatabase);
    }
}
