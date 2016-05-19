package com.giaquino.sample.model.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.giaquino.sample.model.db.contract.UserContract;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class SampleDatabase extends SQLiteOpenHelper implements Database {

    private final SQLiteDatabase database;

    public SampleDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory,
        int version) {
        super(context, name, factory, version);
        database = getWritableDatabase();
    }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.SQL_CREATE_TABLE);
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UserContract.SQL_DROP_TABLE);
        onCreate(db);
    }

    @Override public Cursor query(String table, String selection, String[] selectionArgs) {
        return database.query(table, null, selection, selectionArgs, "", "", "");
    }

    @Override
    public Cursor query(String table, String selection, String[] selectionArgs, String groupBy,
        String having, String orderBy) {
        return database.query(table, null, selection, selectionArgs, groupBy, having, orderBy);
    }

    @Override public int insert(String table, List<ContentValues> contentValues) {
        database.beginTransaction();
        for (ContentValues contentValue : contentValues) {
            database.insert(table, "", contentValue);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
        return contentValues.size();
    }

    @Override
    public int update(String table, ContentValues contentValues, String where, String[] whereArgs) {
        return database.update(table, contentValues, where, whereArgs);
    }

    @Override public int delete(String table, String where, String[] whereArgs) {
        return database.delete(table, where, whereArgs);
    }
}
