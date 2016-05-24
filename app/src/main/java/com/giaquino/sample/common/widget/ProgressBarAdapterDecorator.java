package com.giaquino.sample.common.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.giaquino.sample.common.viewholder.ProgressBarViewHolder;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/18/16
 */
public class ProgressBarAdapterDecorator<T, VH extends ViewHolder, RV extends BaseRecyclerView<T, VH>>
    extends BaseRecyclerView<T, ViewHolder> implements ListDecorator<RV> {

    private final static int VIEW_TYPE_LOADING_INDICATOR = 0;

    private volatile boolean showLoadingIndicator;

    private LayoutInflater inflater;

    private RV delegate;

    public ProgressBarAdapterDecorator(Context context, RV delegate) {
        this.delegate = delegate;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override public int getItemViewType(int position) {
        if (isLoadingIndicatorViewType(position)) return VIEW_TYPE_LOADING_INDICATOR;
        return delegate.getItemViewType(position);
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_LOADING_INDICATOR) {
            return ProgressBarViewHolder.create(inflater, parent);
        }
        return delegate.onCreateViewHolder(parent, viewType);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        if (isLoadingIndicatorViewType(position)) return;
        delegate.onBindViewHolder((VH) holder, position);
    }

    @Override public int getItemCount() {
        return delegate.getItemCount() + (showLoadingIndicator ? 1 : 0);
    }

    @Override public T getData(int position) {
        return delegate.getData(position);
    }

    @Override public void setData(List<T> ts) {
        delegate.setData(ts);
    }

    @Override public RV getDelegate() {
        return delegate;
    }

    @Override public int getDelegateItemCount() {
        return delegate.getItemCount();
    }

    public void setShowLoadingIndicator(boolean showLoadingIndicator) {
        this.showLoadingIndicator = showLoadingIndicator;
    }

    private boolean isLoadingIndicatorViewType(int position) {
        return showLoadingIndicator && position == getItemCount() - 1;
    }
}


