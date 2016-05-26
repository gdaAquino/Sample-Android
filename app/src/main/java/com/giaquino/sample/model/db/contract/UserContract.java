package com.giaquino.sample.model.db.contract;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import com.giaquino.sample.model.db.Database;
import com.giaquino.sample.model.entity.User;
import com.squareup.sqlbrite.SqlBrite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UserContract {

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_AVATAR_URL = "avatar_url";

    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
        + BaseColumns._ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, "
        + COLUMN_ID         + " INTEGER NOT NULL, "
        + COLUMN_LOGIN      + " TEXT NOT NULL, "
        + COLUMN_AVATAR_URL + " TEXT, "
        + "UNIQUE (" + COLUMN_ID + ") ON CONFLICT REPLACE)";

    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    private UserContract() {
    }

    public static class Dao {

        public static final int DEFAULT_DEBOUNCE = 500;

        private Database database;

        public Dao(@NonNull Database database) {
            this.database = database;
        }

        @NonNull public Observable<List<User>> query() {
            return database.query(TABLE_NAME, "", null, "", "", COLUMN_ID + " ASC", "")
                .debounce(DEFAULT_DEBOUNCE, TimeUnit.MILLISECONDS)
                .map(SqlBrite.Query::run)
                .map((Func1<Cursor, List<User>>) cursor -> {
                    if (cursor == null) {
                        return Collections.emptyList();
                    }
                    return toUsers(cursor);
                });
        }

        public int insert(@NonNull List<User> users) {
            return database.insert(TABLE_NAME, toContentValues(users));
        }

        public int delete() {
            return database.delete(TABLE_NAME, "", null);
        }

        @NonNull public List<ContentValues> toContentValues(@NonNull List<User> users) {
            List<ContentValues> list = new ArrayList<>(users.size());
            for (User user : users) {
                ContentValues cv = new ContentValues(3);
                cv.put(COLUMN_ID, user.id());
                cv.put(COLUMN_LOGIN, user.login());
                cv.put(COLUMN_AVATAR_URL, user.avatarUrl());
                list.add(cv);
            }
            return Collections.unmodifiableList(list);
        }

        @NonNull public List<User> toUsers(@NonNull Cursor cursor) {
            int id = cursor.getColumnIndexOrThrow(COLUMN_ID);
            int login = cursor.getColumnIndexOrThrow(COLUMN_LOGIN);
            int avatar = cursor.getColumnIndexOrThrow(COLUMN_AVATAR_URL);
            List<User> list = new ArrayList<>(cursor.getCount());
            while (cursor.moveToNext()) {
                list.add(User.builder()
                    .id(cursor.getInt(id))
                    .login(cursor.getString(login))
                    .avatarUrl(cursor.getString(avatar))
                    .build());
            }
            cursor.close();
            return Collections.unmodifiableList(list);
        }
    }
}
