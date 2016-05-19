package com.giaquino.sample.common.widget;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/19/16
 */
public interface ListDecorator<T> {

    T getDelegate();

    int getDelegateItemCount();

}
