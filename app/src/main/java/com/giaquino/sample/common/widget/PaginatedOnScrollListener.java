package com.giaquino.sample.common.widget;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import timber.log.Timber;

/**
 * @author Gian Darren Azriel Aquino.
 */
public abstract  class PaginatedOnScrollListener extends RecyclerView.OnScrollListener {

    private LinearLayoutManager manager;

    private volatile int page = 1;

    private volatile int previousItemCount;

    private volatile boolean isLoading = true;

    public PaginatedOnScrollListener(LinearLayoutManager manager) {
        this.manager = manager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (dy < 0) return;
        int currentItemCount = manager.getItemCount();
        if (isLoading && currentItemCount > previousItemCount) {
            Timber.d("OnDoneLoadingNextPage %d => %d", previousItemCount, currentItemCount);
            previousItemCount = currentItemCount;
            isLoading = false;
        }
        if (!isLoading && manager.findLastVisibleItemPosition() == currentItemCount - 1) {
            Timber.d("OnLoadNextPage %d", page);
            page++;
            isLoading = true;
            onNextPage(page);
        }
    }

    public void resetPage() {
        previousItemCount = 0;
        page = 1;
        isLoading = true;
    }

    public abstract void onNextPage(int page);
}
