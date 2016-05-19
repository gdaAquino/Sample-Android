package com.giaquino.sample.model.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.BriteDatabase.Transaction;
import com.squareup.sqlbrite.SqlBrite;
import java.util.List;
import rx.Observable;
import rx.Scheduler;
import timber.log.Timber;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/19/16
 */
public class SQLBriteDatabase implements Database {

    private BriteDatabase db;

    public SQLBriteDatabase(SQLiteOpenHelper helper, Scheduler scheduler, boolean debug) {
        db = SqlBrite.create(message -> Timber.d(message)).wrapDatabaseHelper(helper, scheduler);
        db.setLoggingEnabled(debug);
    }

    @Override
    public Observable<Cursor> query(String table, String selection, String[] selectionArgs) {
        StringBuilder builder = builder().append("SELECT * FROM ").append(table);
        appendClause(builder, " WHERE ", selection);
        return db.createQuery(table, builder.toString(), selectionArgs).map(query -> query.run());
    }

    @Override
    public Observable<Cursor> query(String table, String selection, String[] selectionArgs,
        String groupBy, String having, String orderBy, String limit) {
        StringBuilder builder = builder().append("SELECT * FROM ").append(table);
        appendClause(builder, " WHERE ", selection);
        appendClause(builder, " GROUP BY ", groupBy);
        appendClause(builder, " HAVING ", having);
        appendClause(builder, " ORDER BY ", orderBy);
        appendClause(builder, " LIMIT ", limit);
        return db.createQuery(table, builder.toString(), selectionArgs).map(query -> query.run());
    }

    @Override public int insert(String table, List<ContentValues> contentValues) {
        if (contentValues == null || contentValues.size() == 0) return 0;
        Transaction transaction = db.newTransaction();
        try {
            for (ContentValues cv : contentValues) {
                db.insert(table, cv);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
        return contentValues.size();
    }

    @Override
    public int update(String table, ContentValues contentValues, String where, String[] whereArgs) {
        return db.update(table, contentValues, where, whereArgs);
    }

    @Override public int delete(String table, String where, String[] whereArgs) {
        return db.delete(table, where, whereArgs);
    }

    private StringBuilder builder() {
        return new StringBuilder(120);
    }

    private void appendClause(StringBuilder builder, String name, String clause) {
        if (!TextUtils.isEmpty(clause)) {
            builder.append(name);
            builder.append(clause);
        }
    }
}
