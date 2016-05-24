package com.giaquino.sample.ui.users.presenter;

import com.giaquino.sample.common.app.Presenter;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.ui.users.view.UsersView;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UsersPresenter extends Presenter<UsersView> {

    private UserModel userModel;

    public UsersPresenter(UserModel userModel) {
        this.userModel = userModel;
    }

    public void loadUsers(int since) {
        userModel.loadUsers(since);
    }

    @Override public void onBindView() {
        addSubscriptionToUnsubscribe(userModel.users().subscribe(users -> {
            view().setUsers(users);
        }));
        addSubscriptionToUnsubscribe(userModel.errors().subscribe(throwable -> {
            view().showError(throwable.toString());
        }));
    }
}
