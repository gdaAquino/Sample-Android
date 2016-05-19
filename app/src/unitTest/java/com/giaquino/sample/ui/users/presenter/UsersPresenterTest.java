package com.giaquino.sample.ui.users.presenter;

import autovalue.shaded.com.google.common.common.collect.Lists;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.model.entity.User;
import com.giaquino.sample.ui.users.view.UsersView;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Single;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/19/16
 */
public class UsersPresenterTest {

    @Mock private UserModel userModel;
    @Mock private UsersView usersView;

    private List<User> users;
        ;
    private UsersPresenter usersPresenter;

    @Before public void setup() {
        MockitoAnnotations.initMocks(this);
        usersPresenter = new UsersPresenter(userModel);
        usersPresenter.bindView(usersView);
        users = Lists.newArrayList(User.builder().id(1).login("1").avatarUrl("1").build(),
            User.builder().id(2).login("2").avatarUrl("2").build(),
            User.builder().id(3).login("3").avatarUrl("3").build());
    }

    @Test public void loadUsers_withLocalWithInternetShouldSendDataToTheView() {
        when(userModel.getUsersFromLocal()).thenReturn(Single.just(users));
        when(userModel.getUsersFromNetwork(UserModel.SINCE_INITIAL_VALUE)).thenReturn(
            Single.just(users));
        usersPresenter.loadUsers();
        verify(usersView, atLeast(2)).setUsers(users);
        verify(usersView, never()).showErrorMessage(anyString());
    }

    @Test public void loadUsers_withLocalNoInternetShouldSendDataAndErrorToTheView() {
        when(userModel.getUsersFromLocal()).thenReturn(Single.just(users));
        when(userModel.getUsersFromNetwork(UserModel.SINCE_INITIAL_VALUE)).thenReturn(
            Single.error(new Exception()));
        usersPresenter.loadUsers();
        verify(usersView).setUsers(users);
        verify(usersView).showErrorMessage(anyString());
    }

    @Test public void loadUsers_noLocalWithInternetShouldSendDataToTheView() {
        when(userModel.getUsersFromLocal()).thenReturn(Single.just(null));
        when(userModel.getUsersFromNetwork(UserModel.SINCE_INITIAL_VALUE)).thenReturn(
            Single.just(users));
        usersPresenter.loadUsers();
        verify(usersView).setUsers(users);
        verify(usersView, never()).showErrorMessage(anyString());
    }

    @Test public void loadUsers_noLocalNoInternetShouldSendErrorToTheView() {
        when(userModel.getUsersFromLocal()).thenReturn(Single.just(null));
        when(userModel.getUsersFromNetwork(UserModel.SINCE_INITIAL_VALUE)).thenReturn(
            Single.error(new Exception()));
        usersPresenter.loadUsers();
        verify(usersView, never()).setUsers(users);
        verify(usersView).showErrorMessage(anyString());
    }

    @Test public void loadNextUsers_withInternetShouldSendDataToTheView() {
        when(userModel.getUsersFromNetwork(2)).thenReturn(
            Single.just(users));
        usersPresenter.loadNextUsers(2);
        verify(usersView).addUsers(users);
        verify(usersView, never()).showErrorMessage(anyString());
    }

    @Test public void loadNextUsers_noInternetShouldSendErrorToTheView() {
        when(userModel.getUsersFromNetwork(2)).thenReturn(
            Single.error(new Exception()));
        usersPresenter.loadNextUsers(2);
        verify(usersView, never()).addUsers(users);
        verify(usersView).showErrorMessage(anyString());
    }

}
