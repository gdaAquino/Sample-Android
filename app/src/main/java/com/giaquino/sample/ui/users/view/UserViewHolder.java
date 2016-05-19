package com.giaquino.sample.ui.users.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.giaquino.sample.R;
import com.giaquino.sample.common.util.ImageLoader;
import com.giaquino.sample.model.entity.User;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.smp_users_fragment_list_item_image_view_avatar) ImageView imageView;

    @BindView(R.id.smp_users_fragment_list_item_text_view_name) TextView textView;

    private ImageLoader loader;

    public UserViewHolder(View view, ImageLoader loader) {
        super(view);
        this.loader = loader;
        ButterKnife.bind(this, view);
    }

    public static UserViewHolder create(LayoutInflater inflater, ViewGroup container,
        ImageLoader loader) {
        View view = inflater.inflate(R.layout.smp_users_fragment_list_item, container, false);
        return new UserViewHolder(view, loader);
    }

    public void bind(User user) {
        textView.setText(user.login());
        loader.downloadImageInto(user.avatarUrl(), imageView);
    }
}
