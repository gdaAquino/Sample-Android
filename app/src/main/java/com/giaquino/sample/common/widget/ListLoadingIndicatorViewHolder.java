package com.giaquino.sample.common.widget;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.giaquino.sample.R;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/18/16
 */
public class ListLoadingIndicatorViewHolder extends RecyclerView.ViewHolder {

    public ListLoadingIndicatorViewHolder(View itemView) {
        super(itemView);
    }

    public static ListLoadingIndicatorViewHolder create(LayoutInflater inflater,
        ViewGroup container) {
        return new ListLoadingIndicatorViewHolder(
            inflater.inflate(R.layout.smp_view_list_loading_indicator, container, false));
    }
}
