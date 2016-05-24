package com.giaquino.sample.ui.organizations.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.giaquino.sample.model.image.ImageLoader;
import com.giaquino.sample.common.widget.BaseAdapter;
import com.giaquino.sample.model.entity.Organization;
import com.giaquino.sample.ui.organizations.view.OrganizationViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/24/16
 */
public class OrganizationAdapter extends BaseAdapter<Organization, OrganizationViewHolder> {

    private LayoutInflater inflater;
    private ImageLoader imageLoader;
    private List<Organization> organizations = new ArrayList<>();

    public OrganizationAdapter(Context context, ImageLoader imageLoader) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.imageLoader = imageLoader;
    }

    @Override public Organization getData(int position) {
        return organizations.get(position);
    }

    @Override public void setData(List<Organization> organizations) {
        this.organizations.clear();
        this.organizations.addAll(organizations);
    }

    @Override public int getItemViewType(int position) {
        return 1;
    }

    @Override public OrganizationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return OrganizationViewHolder.create(inflater, parent, imageLoader);
    }

    @Override public void onBindViewHolder(OrganizationViewHolder holder, int position) {
        holder.bind(organizations.get(position));
    }

    @Override public int getItemCount() {
        return organizations.size();
    }
}
