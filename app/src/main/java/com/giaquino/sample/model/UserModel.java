package com.giaquino.sample.model;

import com.giaquino.sample.model.api.GithubApi;
import com.giaquino.sample.model.db.Database;
import com.giaquino.sample.model.db.contract.UserContract;
import com.giaquino.sample.model.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import rx.Single;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UserModel {

    public static final int SINCE_INITIAL_VALUE = 1;

    private Database database;

    private GithubApi api;

    /**
     * UnmodifiableList Cache of the first page results.
     */
    private volatile List<User> cache;

    public UserModel(Database database, GithubApi api) {
        this.database = database;
        this.api = api;
    }

    public Single<List<User>> getUsersFromLocal() {
        return Single.fromCallable(new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                if (cache != null) {
                    return cache;
                }
                cache = UserContract.getUsers(database);
                return cache;
            }
        });
    }

    public Single<List<User>> getUsersFromNetwork(final int since) {
        return api.getUsers("ef085af14ec8e343d27c8a329d66a5ae15fc6b92", since) //dont hardcode token
                  .map(new Func1<List<User>, List<User>>() {
                      @Override
                      public List<User> call(List<User> users) {
                          return Collections.unmodifiableList(users);
                      }
                  })
            .doOnSuccess(new Action1<List<User>>() {
                @Override
                public void call(List<User> users) {
                    if (since == SINCE_INITIAL_VALUE) {
                        cache = users;
                        UserContract.insertUsers(database, users);
                    }
                }
                  });
    }
}
