package com.giaquino.sample.model;

import android.support.annotation.NonNull;
import com.giaquino.sample.model.api.GithubApi;
import com.giaquino.sample.model.db.contract.UserContract;
import com.giaquino.sample.model.entity.User;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UserModel {

    private static final String GITHUB_TOKEN = "ef085af14ec8e343d27c8a329d66a5ae15fc6b92";

    private UserContract.Dao userDao;
    private GithubApi githubApi;
    private Observable<List<User>> observableUsers;
    private PublishSubject<Throwable> observableErrors = PublishSubject.create();

    public UserModel(@NonNull UserContract.Dao userDao, @NonNull GithubApi githubApi) {
        this.userDao = userDao;
        this.githubApi = githubApi;
    }

    public void loadUsers() {
        loadUsers(0);
    }

    public void loadUsers(final int since) {
        githubApi.getUsers(GITHUB_TOKEN, since)
            .subscribe(users -> userDao.insert(users),
                throwable -> observableErrors.onNext(throwable));
    }

    public Observable<List<User>> users() {
        if (observableUsers == null) observableUsers = userDao.query();
        return observableUsers;
    }

    public Observable<Throwable> errors() {
        return observableErrors.debounce(1000, TimeUnit.MILLISECONDS);
    }
}
