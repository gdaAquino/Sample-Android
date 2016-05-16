package com.giaquino.sample.ui.users.presenter;

import com.giaquino.sample.common.app.Presenter;
import com.giaquino.sample.model.entity.User;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.ui.users.view.UsersView;

import java.util.List;

import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UsersPresenter extends Presenter<UsersView> {

    private UserModel model;

    public UsersPresenter(UserModel model) {
        this.model = model;
    }

    public void loadUsers() {
        Subscription subscription = model.getUsersFromLocal()
            .concatWith(model.getUsersFromNetwork(UserModel.SINCE_INITIAL_VALUE))
            .subscribe(
                new Action1<List<User>>() { //success
                    @Override
                    public void call(List<User> users) {
                        view().setUsers(users);
                    }
                },
                new Action1<Throwable>() { //failure
                    @Override
                    public void call(Throwable throwable) {
                        view().showErrorMessage("error : " + throwable);
                    }
                },
                new Action0() { //completed
                    @Override
                    public void call() {
                    }
                });
        addSubscriptionToUnsubscribe(subscription);
    }

    public void loadNextUsers(int since) {
        Subscription subscription = model.getUsersFromNetwork(since)
            .subscribe(
                new Action1<List<User>>() {
                    @Override
                    public void call(List<User> users) { //success
                        view().addUsers(users);
                    }
                },
                new Action1<Throwable>() { //failure
                    @Override
                    public void call(Throwable throwable) {
                        view().showErrorMessage("error : " + throwable);
                    }
                });
        addSubscriptionToUnsubscribe(subscription);
    }
}
