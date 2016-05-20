package com.giaquino.sample.model.db;

import android.content.ContentValues;
import android.database.Cursor;
import com.squareup.sqlbrite.QueryObservable;
import java.util.List;
import rx.Observable;

/**
 * @author Gian Darren Azriel Aquino.
 */
public interface Database {

    QueryObservable query(String table, String selection, String[] selectionArgs);

    QueryObservable query(String table, String selection, String[] selectionArgs, String groupBy,
        String having, String orderBy, String limit);

    int insert(String table, List<ContentValues> contentValues);

    int update(String table, ContentValues contentValues, String where, String[] whereArgs);

    int delete(String table, String where, String[] whereArgs);
}
