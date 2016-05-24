package com.giaquino.sample.ui.users.presenter;

import com.giaquino.sample.common.app.Presenter;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.ui.users.view.UsersView;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UsersPresenter extends Presenter<UsersView> {

    private UserModel model;

    public UsersPresenter(UserModel model) {
        this.model = model;
    }

    public void loadUsers(int since) {
        model.loadUsers(since);
    }

    @Override public void onBindView() {
        addSubscriptionToUnsubscribe(model.users().subscribe(users -> {
            view().setUsers(users);
        }));
        addSubscriptionToUnsubscribe(model.errors().subscribe(throwable -> {
            view().showErrorMessage(throwable.toString());
        }));
    }
}
