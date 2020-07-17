package com.ujjawalayush.example.kamsh;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.ujjawalayush.example.kamsh.Data.Drafts;

public class DBAdapter {
    static final String KEY_ROWID = "id";
    static final String KEY_ROWID1 = "id1";
    static final String KEY_TITLE1 = "title1";
    static final String KEY_TYPE = "type";
    static final String KEY_CONTENT = "content";
    static final String KEY_MAIN="main_title";
    static final String KEY_POSITION1 = "position1";
    static final String KEY_TITLE = "title";
    static final String DATABASE_NAME = "IMP_FILES";
    static final String DATABASE_TABLE = "IMP_FILES";
    static final String DATABASE_TABLE1 = "IMP_TITLES";
    static final String TAG = "DBAdapter";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement,"+KEY_MAIN+" text not null,"+KEY_TYPE+" text not null,"+KEY_TITLE+" text not null,"+KEY_CONTENT+" text not null);";
    private final Context context;
    static final String DATABASE_CREATE1 = "create table "+DATABASE_TABLE1+" ("+KEY_ROWID1+" integer primary key autoincrement,"+KEY_TITLE1+" text not null,"+KEY_POSITION1+" text not null);";
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;
    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE1);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE");
            db.execSQL("DROP TABLE IF EXISTS DATABASE_CREATE1");
            onCreate(db);
        }
    }
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }
    public long insertContent(String main_title,String type,String title,String content)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_MAIN,main_title);
        initialValues.put(KEY_TYPE,type);
        initialValues.put(KEY_TITLE,title);
        initialValues.put(KEY_CONTENT,content);
        return db.insert(DATABASE_TABLE, null,initialValues);
    }
    public long insertTitle(String main_title,String position)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE1,main_title);
        initialValues.put(KEY_POSITION1,position);
        return db.insert(DATABASE_TABLE1, null,initialValues);
    }
    public Cursor getAllContents()
    {
        return db.query(DATABASE_TABLE, new String[] {
                        KEY_MAIN,KEY_TYPE,KEY_TITLE,KEY_CONTENT},
                null,null,
                null,
                null,
                null);
    }
    public Cursor getAllTitles()
    {
        return db.query(DATABASE_TABLE1, new String[] {
                        KEY_ROWID1,
                        KEY_TITLE1,KEY_POSITION1},
                null,
                null,
                null,
                null,
                null);
    }
    public void deleteTitle(String name)
    {
        String where=KEY_TITLE1+"=?";
        db.delete(DATABASE_TABLE1, where, new String[]{name}) ;
    }
    public void UpdateTitle(Drafts name)
    {
        String where=KEY_TITLE1+"=?";
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TITLE1,name.getTitle());
        initialValues.put(KEY_POSITION1,name.getPostion());
        db.update(DATABASE_TABLE1,initialValues, where, new String[]{name.getTitle()}) ;
    }
    public void deleteContent(String name)
    {
        String where=KEY_MAIN+"=?";
        db.delete(DATABASE_TABLE, where, new String[]{name}) ;
    }
}
