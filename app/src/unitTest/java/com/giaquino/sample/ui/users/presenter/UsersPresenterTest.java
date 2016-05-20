package com.giaquino.sample.ui.users.presenter;

import autovalue.shaded.com.google.common.common.collect.Lists;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.model.entity.User;
import com.giaquino.sample.ui.users.view.UsersView;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import rx.subjects.PublishSubject;

import static org.mockito.Matchers.anyListOf;
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

    private List<User> empty;
    private List<User> users;
    private UsersPresenter usersPresenter;
    private PublishSubject<List<User>> userObservable;
    private PublishSubject<Throwable> errorObservable;

    @Before public void setup() {
        MockitoAnnotations.initMocks(this);
        empty = new ArrayList<>();
        users = Lists.newArrayList(User.builder().id(1).login("1").avatarUrl("1").build(),
            User.builder().id(2).login("2").avatarUrl("2").build(),
            User.builder().id(3).login("3").avatarUrl("3").build());
        usersPresenter = new UsersPresenter(userModel);
        userObservable = PublishSubject.create();
        errorObservable = PublishSubject.create();
    }

    @Test public void bindView_withLocalShouldSendDataToView() {
        when(userModel.users()).thenReturn(userObservable.startWith(users).asObservable());
        when(userModel.errors()).thenReturn(errorObservable.asObservable());
        usersPresenter.bindView(usersView);
        verify(usersView).setUsers(anyListOf(User.class));
        verify(usersView, never()).showErrorMessage(anyString());
    }

    @Test public void bindView_noLocalShouldSendEmptyDataToView() {
        when(userModel.users()).thenReturn(userObservable.startWith(empty).asObservable());
        when(userModel.errors()).thenReturn(errorObservable.asObservable());
        usersPresenter.bindView(usersView);
        verify(usersView).setUsers(anyListOf(User.class));
        verify(usersView, never()).showErrorMessage(anyString());
    }

    @Test public void loadUsers_withLocalWithInternetShouldSendDataToView() {
        Mockito.doAnswer(invocation -> {
            userObservable.onNext(users);
            return null;
        }).when(userModel).loadUsers();
        when(userModel.users()).thenReturn(userObservable.startWith(users).asObservable());
        when(userModel.errors()).thenReturn(errorObservable.asObservable());
        usersPresenter.bindView(usersView);
        usersPresenter.loadUsers();
        verify(usersView, atLeast(2)).setUsers(anyListOf(User.class));
        verify(usersView, never()).showErrorMessage(anyString());
    }

    @Test public void loadUsers_withLocalNoInternetShouldSendDataAndErrorToView() {
        Mockito.doAnswer(invocation -> {
            errorObservable.onNext(new Exception());
            return null;
        }).when(userModel).loadUsers();
        when(userModel.users()).thenReturn(userObservable.startWith(users).asObservable());
        when(userModel.errors()).thenReturn(errorObservable.asObservable());
        usersPresenter.bindView(usersView);
        usersPresenter.loadUsers();
        verify(usersView).setUsers(anyListOf(User.class));
        verify(usersView).showErrorMessage(anyString());
    }

    @Test public void loadUsers_noLocalWithInternetShouldSendDataToView() {
        Mockito.doAnswer(invocation -> {
            userObservable.onNext(users);
            return null;
        }).when(userModel).loadUsers();
        when(userModel.users()).thenReturn(userObservable.startWith(empty).asObservable());
        when(userModel.errors()).thenReturn(errorObservable.asObservable());
        usersPresenter.bindView(usersView);
        usersPresenter.loadUsers();
        verify(usersView).setUsers(users);
        verify(usersView, never()).showErrorMessage(anyString());
    }

    @Test public void loadUsers_noLocalNoInternetShouldSendErrorToView() {
        Mockito.doAnswer(invocation -> {
            errorObservable.onNext(new Exception());
            return null;
        }).when(userModel).loadUsers();
        when(userModel.users()).thenReturn(userObservable.startWith(empty).asObservable());
        when(userModel.errors()).thenReturn(errorObservable.asObservable());
        usersPresenter.bindView(usersView);
        usersPresenter.loadUsers();
        verify(usersView, never()).setUsers(users);
        verify(usersView).showErrorMessage(anyString());
    }
}
