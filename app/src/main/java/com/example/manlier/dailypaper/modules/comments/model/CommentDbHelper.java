package com.example.manlier.dailypaper.modules.comments.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.manlier.dailypaper.contracts.DailyPaperContract.*;

/**
 * Created by manlier on 2017/5/16.
 */

public class CommentDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DailyPaper.db";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + CommentEntry.TABLE_NAME + " (" +
                    CommentEntry._ID + " INTEGER PRIMARY KEY," +
                    CommentEntry.COLUMN_NAME_OBSERVER + " TEXT," +
                    CommentEntry.COLUMN_NAME_COMMENT + " TEXT," +
                    CommentEntry.COLUMN_NAME_DATE + " TEXT," +
                    CommentEntry.COLUMN_NAME_DOC_ID + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + CommentEntry.TABLE_NAME;


    public CommentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public boolean insertData(String observer, String comment, String date, String docId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CommentEntry.COLUMN_NAME_OBSERVER, observer);
        contentValues.put(CommentEntry.COLUMN_NAME_COMMENT, comment);
        contentValues.put(CommentEntry.COLUMN_NAME_DATE, date);
        contentValues.put(CommentEntry.COLUMN_NAME_DOC_ID, docId);
        long result = db.insert(CommentEntry.TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllComments(String docId) {
        SQLiteDatabase db = getWritableDatabase();

        // 要查询的字段
        String[] projection = {
                CommentEntry._ID,
                CommentEntry.COLUMN_NAME_OBSERVER,
                CommentEntry.COLUMN_NAME_COMMENT,
                CommentEntry.COLUMN_NAME_DATE,
                CommentEntry.COLUMN_NAME_DOC_ID
        };

        // 过滤结果，WHERE "docId" = 'docId'
        String selection = CommentEntry.COLUMN_NAME_DOC_ID + " = ?";
        String[] selectionArgs = {docId};

        // 升序排列
        String sortOrder =
                CommentEntry.COLUMN_NAME_DATE + " ASC";

        return db.query(
                CommentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);
    }

    public Cursor getPieceComment(String docId, int from, int len) {
        SQLiteDatabase db = getWritableDatabase();

        // 要查询的字段
        String[] projection = {
                CommentEntry._ID,
                CommentEntry.COLUMN_NAME_OBSERVER,
                CommentEntry.COLUMN_NAME_COMMENT,
                CommentEntry.COLUMN_NAME_DATE,
                CommentEntry.COLUMN_NAME_DOC_ID
        };

        // 过滤结果，WHERE "docId" = 'docId'
        String selection = CommentEntry.COLUMN_NAME_DOC_ID + " = ?";
        String[] selectionArgs = {docId};

        // 升序排列
        String sortOrder =
                CommentEntry.COLUMN_NAME_DATE + " ASC";

        String limit = from + "," + (from + len);

        return db.query(
                CommentEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder,
                limit);
    }
}
