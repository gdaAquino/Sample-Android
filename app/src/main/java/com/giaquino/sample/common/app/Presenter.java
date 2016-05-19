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
            throw new IllegalStateException("Presenter already has a view.");
        }
        this.view = view;
        onBindView();
    }

    public void unbindView() {
        if (view == null) {
            throw new IllegalStateException("View == null, Nothing to unbind.");
        }
        view = null;
        subscriptions.clear();
    }

    public void addSubscriptionToUnsubscribe(Subscription subscription) {
        subscriptions.add(subscription);
    }

    public abstract void onBindView();

}
