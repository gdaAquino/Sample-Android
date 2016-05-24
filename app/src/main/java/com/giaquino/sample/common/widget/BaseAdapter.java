package com.giaquino.sample.common.widget;

import android.support.v7.widget.RecyclerView;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/19/16
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder>
    extends RecyclerView.Adapter<VH> {

    public abstract T getData(int position);

    public abstract void setData(List<T> t);

}
