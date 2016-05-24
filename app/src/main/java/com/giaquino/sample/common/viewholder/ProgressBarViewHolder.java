package com.giaquino.sample.common.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.giaquino.sample.R;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/18/16
 */
public class ProgressBarViewHolder extends RecyclerView.ViewHolder {

    public ProgressBarViewHolder(View itemView) {
        super(itemView);
    }

    public static ProgressBarViewHolder create(LayoutInflater inflater, ViewGroup container) {
        return new ProgressBarViewHolder(
            inflater.inflate(R.layout.smp_view_list_item_progress_bar, container, false));
    }
}
