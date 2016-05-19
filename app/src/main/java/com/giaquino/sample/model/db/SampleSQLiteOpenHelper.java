package com.giaquino.sample.model.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.giaquino.sample.model.db.contract.UserContract;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class SampleSQLiteOpenHelper extends SQLiteOpenHelper {

    public SampleSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
        int version) {
        super(context, name, factory, version);
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.SQL_CREATE_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserContract.SQL_DROP_TABLE);
        onCreate(db);
    }
}
