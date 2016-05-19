package com.giaquino.sample.common.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class DirectionalOnScrollListener extends RecyclerView.OnScrollListener {

    private volatile boolean notifyDownScroll = true;
    private volatile boolean notifyUpScroll = true;
    private volatile boolean notifyScroll = true;

    private LinearLayoutManager manager;

    public DirectionalOnScrollListener(LinearLayoutManager manager) {
        this.manager = manager;
    }

    @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        int count = manager.getItemCount();
        if (count == 0) {
            return;
        }
        int first = manager.findFirstVisibleItemPosition();
        int last = manager.findLastVisibleItemPosition();
        if (notifyScroll) onScroll(first, last, count);
        if (dy < 0 && notifyUpScroll) {
            onScrollUp(first, last, count);
        } else if (notifyDownScroll) onScrollDown(first, last, count);
    }

    public void setNotifyDownScroll(boolean notify) {
        this.notifyDownScroll = notify;
    }

    public void setNotifyUpScroll(boolean notify) {
        this.notifyUpScroll = notify;
    }

    public void setNotifyScroll(boolean notify) {
        this.notifyScroll = notify;
    }

    public void onScrollDown(int firstItemIndex, int lastItemIndex, int totalItemCount) {
    }

    public void onScrollUp(int firstItemIndex, int lastItemIndex, int totalItemCount) {
    }

    public void onScroll(int firstItemIndex, int lastItemIndex, int totalItemCount) {
    }
}
