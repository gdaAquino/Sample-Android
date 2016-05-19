package com.giaquino.sample.model.db;

import android.content.ContentValues;
import android.database.Cursor;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino.
 */
public interface Database {

    Cursor query(String table, String selection, String[] selectionArgs);

    Cursor query(String table, String selection, String[] selectionArgs, String groupBy,
        String having, String orderBy);

    int insert(String table, List<ContentValues> contentValues);

    int update(String table, ContentValues contentValues, String where, String[] whereArgs);

    int delete(String table, String where, String[] whereArgs);
}
