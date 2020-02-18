package com.nbw.lineplusmemoapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nbw.lineplusmemoapp.tables.ImageTable;
import com.nbw.lineplusmemoapp.tables.MemoTable;

//sqlite사용
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "Lineplusmemo.db";
    public static final int DB_VERSION = 1;
    public static final String CREATE_MEMO_TABLE =
            String.format(
                    "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT);",
                    MemoTable.MemoEntry.MEMO_TABLE_NAME,
                    MemoTable.MemoEntry._ID,
                    MemoTable.MemoEntry.COLUMN_NAME_TITLE,
                    MemoTable.MemoEntry.COLUMN_NAME_CONTENT);

    public static final String DELETE_MEMO_TABLE = "DROP TABLE IF EXISTS " + MemoTable.MemoEntry.MEMO_TABLE_NAME;

    public static final String CREATE_IMG_TABLE =
            String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER);",
                    ImageTable.ImageEntry.IMG_TABLE_NAME,
                    ImageTable.ImageEntry._ID,
                    ImageTable.ImageEntry.COLUMN_NAME_IMG,
                    ImageTable.ImageEntry.COLUMN_NAME_MEMO_INDEX);

    public static final String DELETE_IMG_TABLE = "DROP TABLE IF EXISTS " + ImageTable.ImageEntry.IMG_TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_MEMO_TABLE);
        sqLiteDatabase.execSQL(CREATE_IMG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (newVersion == DB_VERSION) {
            sqLiteDatabase.execSQL(DELETE_MEMO_TABLE);
            sqLiteDatabase.execSQL(DELETE_IMG_TABLE);
            onCreate(sqLiteDatabase);
        }
    }

}
