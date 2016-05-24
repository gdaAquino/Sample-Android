package com.giaquino.sample.common.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.giaquino.sample.common.viewholder.ProgressBarViewHolder;
import com.giaquino.sample.common.widget.BaseAdapter;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/18/16
 */
public class ProgressBarAdapterDecorator<T, VH extends ViewHolder, A extends BaseAdapter<T, VH>>
    extends BaseAdapter<T, ViewHolder> {

    private final static int VIEW_TYPE_PROGRESS_BAR = 0;

    private volatile boolean isRefreshing;

    private LayoutInflater inflater;

    private A delegate;

    public ProgressBarAdapterDecorator(Context context, A delegate) {
        this.delegate = delegate;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override public int getItemViewType(int position) {
        if (isProgressBarViewType(position)) return VIEW_TYPE_PROGRESS_BAR;
        return delegate.getItemViewType(position);
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_PROGRESS_BAR) {
            return ProgressBarViewHolder.create(inflater, parent);
        }
        return delegate.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        if (isProgressBarViewType(position)) return;
        delegate.onBindViewHolder((VH) holder, position);
    }

    @Override public int getItemCount() {
        return delegate.getItemCount() + (isRefreshing ? 1 : 0);
    }

    @Override public T getData(int position) {
        int delegateItemLastPosition = delegate.getItemCount() - 1;
        if (position > delegateItemLastPosition) {
            position = delegateItemLastPosition;
        }
        return delegate.getData(position);
    }

    @Override public void setData(List<T> ts) {
        delegate.setData(ts);
    }

    public void setRefreshing(boolean refreshing) {
        this.isRefreshing = refreshing;
    }

    private boolean isProgressBarViewType(int position) {
        return isRefreshing && position == getItemCount() - 1;
    }
}


