package com.giaquino.sample.common.app;

import org.junit.Before;
import org.junit.Test;
import rx.Subscription;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/19/16
 */
public class PresenterTest {

    Object view;

    private Presenter<Object> presenter;

    @Before
    public void setup() {
        view = new Object();
        presenter = new Presenter<>();
    }

    @Test
    public void bindView_shouldBindViewToThePresenter() {
        presenter.bindView(view);
        assertThat(presenter.view(), is(view));
    }

    @Test(expected = IllegalStateException.class)
    public void bindView_shouldThrowAnErrorIfAlreadyBinded() {
        presenter.bindView(view);
        presenter.bindView(view);
    }

    @Test
    public void unbindView_shouldUnbindViewToThePresenter() {
        presenter.bindView(view);
        presenter.unbindView();
        assertThat(presenter.view(), nullValue());
    }

    @Test
    public void unbindView_shouldUnsubcribeSubscriptions() {
        presenter.bindView(view);
        Subscription subscription1 = mock(Subscription.class);
        Subscription subscription2 = mock(Subscription.class);
        Subscription subscription3 = mock(Subscription.class);

        presenter.addSubscriptionToUnsubscribe(subscription1);
        presenter.addSubscriptionToUnsubscribe(subscription2);
        presenter.addSubscriptionToUnsubscribe(subscription3);
        verify(subscription1, never()).unsubscribe();
        verify(subscription2, never()).unsubscribe();
        verify(subscription3, never()).unsubscribe();

        presenter.unbindView();
        verify(subscription1).unsubscribe();
        verify(subscription2).unsubscribe();
        verify(subscription3).unsubscribe();
    }

    @Test(expected = IllegalStateException.class)
    public void unbindView_shouldThrowAnErrorIfAlreadyUnbinded() {
        presenter.unbindView();
    }

    @Test
    public void view_shouldReturnTheCorrectView() {
        presenter.bindView(view);
        assertThat(presenter.view(), equalTo(view));
    }

    @Test
    public void view_shouldReturnNullByDefault() {
        assertThat(presenter.view(), nullValue());
    }
}
