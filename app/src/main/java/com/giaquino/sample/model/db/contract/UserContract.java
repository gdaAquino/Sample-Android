package com.giaquino.sample.model.db.contract;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;
import com.giaquino.sample.model.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino.
 */
public final class UserContract {

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

    @NonNull public static List<ContentValues> toContentValues(@NonNull List<User> users) {
        int size = users.size();
        List<ContentValues> contentValues = new ArrayList<>(size);
        for (int x = 0; x < size; x++) {
            ContentValues cv = new ContentValues(3);
            User user = users.get(x);
            cv.put(COLUMN_ID, user.id());
            cv.put(COLUMN_LOGIN, user.login());
            cv.put(COLUMN_AVATAR_URL, user.avatarUrl());
            contentValues.add(cv);
        }
        return Collections.unmodifiableList(contentValues);
    }

    @NonNull
    public static List<User> toUsers(@NonNull Cursor cursor) {
        if (cursor.isClosed() || cursor.getCount() == 0) {
            return Collections.emptyList();
        }
        int id = cursor.getColumnIndexOrThrow(COLUMN_ID);
        int login = cursor.getColumnIndexOrThrow(COLUMN_LOGIN);
        int avatar = cursor.getColumnIndexOrThrow(COLUMN_AVATAR_URL);
        List<User> users = new ArrayList<>(cursor.getCount());
        while (cursor.moveToNext()) {
            users.add(User.builder()
                .id(cursor.getInt(id))
                .login(cursor.getString(login))
                .avatarUrl(cursor.getString(avatar))
                .build());
        }
        cursor.close();
        return Collections.unmodifiableList(users);
    }
}
