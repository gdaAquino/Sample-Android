package com.giaquino.sample.model.db.contract;

import android.content.ContentValues;
import android.database.Cursor;
import android.provider.BaseColumns;

import com.giaquino.sample.model.db.Database;
import com.giaquino.sample.model.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class provides the contract of {@link User} for the database as well as transforming
 * methods {@link #transformToContentValues(List)} and {@link #transformToUsers(Cursor)}.
 *
 * @author Gian Darren Azriel Aquino.
 */
public final class UserContract {

    private UserContract() { //no need for instantiating this class
    }

    public static final String TABLE_NAME = "users";

    /**
     * Columns
     */
    public static final String COLUMN_ID         = "id";
    public static final String COLUMN_LOGIN      = "login";
    public static final String COLUMN_AVATAR_URL = "avatar_url";

    /**
     * SQL Statement for creating the User Table.
     */
    public static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
        + BaseColumns._ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " //required android id
        + COLUMN_ID         + " INTEGER NOT NULL, "
        + COLUMN_LOGIN      + " TEXT NOT NULL, "
        + COLUMN_AVATAR_URL + " TEXT, "
        + "UNIQUE (" + COLUMN_ID + ") ON CONFLICT REPLACE)";

    /**
     * SQL Statement for dropping the User Table.
     */
    public static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * Transforms {@link List<User>} to {@link List<ContentValues>}.
     *
     * @param users The {@link List<User>} to be transformed to {@link List<ContentValues>}.
     * @return A {@link List<ContentValues>}, which is transformed from {@link List<User>}.
     */
    public static List<ContentValues> transformToContentValues(List<User> users) {
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

    /**
     * Transforms the {@link Cursor} to {@link List<User>}.
     *
     * <p>This method will throw {@link IllegalArgumentException} if the {@link Cursor} does not
     * contain the correct columns.</p>
     *
     * <p>This method will close the {@link Cursor}.
     *
     * @param cursor The {@link Cursor}, which is the results of a query for the user's table.
     * @return The Unmodifiable {@link List<User>}, which is transformed from {@link Cursor}.
     * @throws IllegalArgumentException
     */
    public static List<User> transformToUsers(Cursor cursor) {
        if (cursor == null || cursor.isClosed() || cursor.getCount() == 0) {
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

    /**
     * Query all {@link User} from the database.
     *
     * @param database The {@link Database} where {@link User} will be queried.
     * @return The {@link List<User>} results from the query.
     */
    public static List<User> getUsers(Database database) {
        return transformToUsers(database.query(TABLE_NAME, "", null));
    }

    /**
     * Insert {@link List<User>} to the database.
     *
     * @param users The {@link List<User>} to be inserted to the database.
     * @param database The {@link Database}
     */
    public static void insertUsers(Database database, List<User> users) {
        database.insert(TABLE_NAME, transformToContentValues(users));
    }
}
