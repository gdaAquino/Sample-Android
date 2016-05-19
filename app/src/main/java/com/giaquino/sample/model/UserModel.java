package com.giaquino.sample.model;

import android.database.Cursor;
import android.support.annotation.NonNull;
import com.giaquino.sample.model.api.GithubApi;
import com.giaquino.sample.model.db.Database;
import com.giaquino.sample.model.db.contract.UserContract;
import com.giaquino.sample.model.entity.User;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Func1;
import rx.subjects.PublishSubject;

import static com.giaquino.sample.model.db.contract.UserContract.TABLE_NAME;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UserModel {

    private Database database;
    private GithubApi githubApi;
    private Observable<List<User>> users;
    private PublishSubject<Throwable> error = PublishSubject.create();

    public UserModel(@NonNull Database database, @NonNull GithubApi githubApi) {
        this.database = database;
        this.githubApi = githubApi;
        this.users = database.query(TABLE_NAME, "", null)
            .map((Func1<Cursor, List<User>>) cursor -> {
                if (cursor == null) return Collections.emptyList();
                return UserContract.toUsers(cursor);
            });
    }

    public void loadUsers() {
        loadUsers(1);
    }

    public void loadUsers(final int since) {
        githubApi.getUsers("ef085af14ec8e343d27c8a329d66a5ae15fc6b92", since).subscribe(users1 -> {
            if (since == 1) database.delete(TABLE_NAME, "", null);
            database.insert(TABLE_NAME, UserContract.toContentValues(users1));
        }, throwable -> error.onNext(throwable));
    }

    public Observable<List<User>> users() {
        return users.throttleLast(1, TimeUnit.SECONDS);
    }

    public Observable<Throwable> error() {
        return error.asObservable().throttleLast(1, TimeUnit.SECONDS);
    }
}
