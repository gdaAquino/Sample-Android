package com.giaquino.sample.common.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/18/16
 */
public class ListLoadingAdapterDecorator<T extends RecyclerView.Adapter>
    extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static int VIEW_TYPE_LOADING_INDICATOR = 0;

    private volatile boolean showLoadingIndicator;

    private LayoutInflater inflater;

    private T delegate;

    public ListLoadingAdapterDecorator(Context context, T delegate) {
        this.delegate = delegate;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override public int getItemViewType(int position) {
        if (isLoadingIndicatorViewType(position)) return VIEW_TYPE_LOADING_INDICATOR;
        return delegate.getItemViewType(position);
    }

    @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING_INDICATOR) {
            return ListLoadingIndicatorViewHolder.create(inflater, parent);
        }
        return delegate.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (isLoadingIndicatorViewType(position)) return;
        delegate.onBindViewHolder(holder, position);
    }

    @Override public int getItemCount() {
        return delegate.getItemCount() + (showLoadingIndicator ? 1 : 0);
    }

    public T getDelegate() {
        return delegate;
    }

    public void showLoadingIndicator(boolean notify) {
        showLoadingIndicator = true;
        if (notify) notifyDataSetChanged();
    }

    public void hideLoadingIndicator(boolean notify) {
        showLoadingIndicator = false;
        if (notify) notifyDataSetChanged();
    }

    private boolean isLoadingIndicatorViewType(int position) {
        return showLoadingIndicator && position == getItemCount() - 1;
    }
}
