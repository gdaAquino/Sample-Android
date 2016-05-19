package com.giaquino.sample.ui.users.presenter;

import com.giaquino.sample.common.app.Presenter;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.ui.users.view.UsersView;

import rx.Subscription;

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
                users -> view().setUsers(users), //success
                throwable -> view().showErrorMessage("error : " + throwable), //error
                () -> {}); //completed
        addSubscriptionToUnsubscribe(subscription);
    }

    public void loadNextUsers(int since) {
        Subscription subscription = model.getUsersFromNetwork(since)
            .subscribe(
                users -> view().addUsers(users), //success
                throwable -> view().showErrorMessage("error : " + throwable)); //error
        addSubscriptionToUnsubscribe(subscription);
    }
}
