package com.giaquino.sample.ui.organizations.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.giaquino.sample.R;
import com.giaquino.sample.model.image.ImageLoader;
import com.giaquino.sample.model.entity.Organization;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/24/16
 */
public class OrganizationViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.smp_view_list_item_organizations_text_view_name) TextView name;
    @BindView(R.id.smp_view_list_item_organizations_text_view_desc) TextView description;
    @BindView(R.id.smp_view_list_item_organizations_image_view_avatar) ImageView avatar;

    private ImageLoader imageLoader;

    public static OrganizationViewHolder create(LayoutInflater inflater, ViewGroup container,
        ImageLoader imageLoader) {
        return new OrganizationViewHolder(
            inflater.inflate(R.layout.smp_view_list_item_organization, container, false),
            imageLoader);
    }

    public OrganizationViewHolder(View view, ImageLoader imageLoader) {
        super(view);
        this.imageLoader = imageLoader;
        ButterKnife.bind(this, view);
    }

    public void bind(Organization organization) {
        name.setText(organization.login());
        description.setText(organization.description());
        imageLoader.downloadImageInto(organization.avatarUrl(), avatar);
    }
}
