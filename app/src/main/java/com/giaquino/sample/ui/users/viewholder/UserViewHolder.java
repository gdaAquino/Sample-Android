package com.giaquino.sample.ui.users.viewholder;

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
import com.giaquino.sample.model.entity.User;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.smp_view_list_item_user_image_view_avatar) ImageView avatar;
    @BindView(R.id.smp_view_list_item_user_text_view_name) TextView name;

    private ImageLoader imageLoader;

    public static UserViewHolder create(LayoutInflater inflater, ViewGroup container,
        ImageLoader loader) {
        return new UserViewHolder(
            inflater.inflate(R.layout.smp_view_list_item_user, container, false), loader);
    }

    public UserViewHolder(View view, ImageLoader imageLoader) {
        super(view);
        this.imageLoader = imageLoader;
        ButterKnife.bind(this, view);
    }

    public void bind(User user) {
        name.setText(user.login());
        imageLoader.downloadImageInto(user.avatarUrl(), avatar);
    }
}
