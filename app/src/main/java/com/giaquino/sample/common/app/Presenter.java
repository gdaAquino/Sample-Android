package com.giaquino.sample.common.app;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Gian Darren Azriel Aquino.
 */
public abstract class Presenter<V> {

    private CompositeSubscription subscriptions = new CompositeSubscription();

    private volatile V view;

    public V view() {
        return view;
    }

    public void bindView(V view) {
        if (this.view != null) {
            throw new IllegalArgumentException("Presenter is already binded to a view.");
        }
        this.view = view;
    }

    public void unbindView() {
        if (view == null) {
            throw new IllegalArgumentException("Presenter is not binded to a view.");
        }
        view = null;
        subscriptions.clear();
    }

    public void addSubscriptionToUnsubscribe(Subscription subscription) {
        subscriptions.add(subscription);
    }
}
