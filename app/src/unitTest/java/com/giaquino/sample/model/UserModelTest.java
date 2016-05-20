package com.giaquino.sample.model;

import autovalue.shaded.com.google.common.common.collect.Lists;
import com.giaquino.sample.model.api.GithubApi;
import com.giaquino.sample.model.db.contract.UserContract;
import com.giaquino.sample.model.entity.User;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import rx.Observable;
import rx.Single;
import rx.subjects.PublishSubject;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/20/16
 */
public class UserModelTest {

    @Mock private UserContract.Dao userDao;
    @Mock private GithubApi githubApi;

    private int errorsCounter;
    private int usersCounter;
    private List<User> users;
    private UserModel userModel;
    private PublishSubject<List<User>> usersPublisher = PublishSubject.create();

    @Before public void setup() {
        MockitoAnnotations.initMocks(this);
        userModel = new UserModel(userDao, githubApi);
        users = Lists.newArrayList(User.builder().id(1).login("1").avatarUrl("1").build(),
            User.builder().id(2).login("2").avatarUrl("2").build(),
            User.builder().id(3).login("3").avatarUrl("3").build());
        userModel.errors().subscribe(throwable -> { //subscribe to errors and increment counter
            errorsCounter += 1;
        });
        //return a publisher that can send an update
        when(userDao.query()).thenReturn(
            usersPublisher.debounce(1000, TimeUnit.MILLISECONDS).asObservable());
        userModel.users().subscribe(users1 -> { //subscribe to users and increment counter
            usersCounter += 1;
        });
    }

    @Test public void users_shouldReturnObservable() {
        assertThat(userModel.users(), CoreMatchers.any(Observable.class));
    }

    @Test public void errors_shouldReturnObservable() {
        assertThat(userModel.errors(), CoreMatchers.any(Observable.class));
    }

    @Test public void loadUsers_withInternetShouldLoadUsersAndInsertToDatabaseAndSendAnUpdate() {
        when(githubApi.getUsers(anyString(), anyInt())).thenReturn(Single.just(users));
        when(userDao.insert(eq(users))).thenAnswer(invocation -> {
            usersPublisher.onNext(users); //send an update when insert is called
            return null;
        }).thenReturn(users.size());
        userModel.loadUsers();
        sleep();
        verify(githubApi).getUsers(anyString(), anyInt());
        verify(userDao).insert(eq(users));
        assertThat(usersCounter, CoreMatchers.is(1));
    }

    @Test
    public void multipleLoadUsers_withInternetShouldLoadUsersAndInsertToDatabaseAndSendOneUpdateWithinOneSecond() {
        when(githubApi.getUsers(anyString(), anyInt())).thenReturn(Single.just(users));
        when(userDao.insert(eq(users))).thenAnswer(invocation -> {
            usersPublisher.onNext(users); //send an update when insert is called
            return null;
        }).thenReturn(users.size());
        userModel.loadUsers();
        userModel.loadUsers();
        userModel.loadUsers();
        userModel.loadUsers();
        userModel.loadUsers();
        sleep();
        verify(githubApi, atLeast(5)).getUsers(anyString(), anyInt());
        verify(userDao, atLeast(5)).insert(eq(users));
        assertThat(usersCounter, CoreMatchers.is(1));
    }

    @Test public void loadUsers_noInternetShouldLoadUsersAndThrowAnError() {
        when(githubApi.getUsers(anyString(), anyInt())).thenReturn(PublishSubject.create(
            (Observable.OnSubscribe<List<User>>) subscriber -> subscriber.onError(new Exception()))
            .toSingle());
        userModel.loadUsers();
        sleep(); //sleep due to delay (debounce) on errors observable
        verify(githubApi).getUsers(anyString(), anyInt());
        verify(userDao, never()).insert(anyListOf(User.class));
        assertThat(errorsCounter, CoreMatchers.is(1));
    }

    @Test public void multipleLoadUsers_noInternetShouldLoadUsersAndThrowOneErrorWithinOneSecond() {
        when(githubApi.getUsers(anyString(), anyInt())).thenReturn(PublishSubject.create(
            (Observable.OnSubscribe<List<User>>) subscriber -> subscriber.onError(new Exception()))
            .toSingle());
        userModel.loadUsers();
        userModel.loadUsers();
        userModel.loadUsers();
        userModel.loadUsers();
        userModel.loadUsers();
        sleep(); //sleep due to delay (debounce) on errors observable
        verify(githubApi, atLeast(5)).getUsers(anyString(), anyInt());
        verify(userDao, never()).insert(anyListOf(User.class));
        assertThat(errorsCounter, CoreMatchers.is(1));
    }

    public void sleep() {
        try {
            Thread.sleep(1100);
        } catch (InterruptedException ignored) {
        }
    }
}
