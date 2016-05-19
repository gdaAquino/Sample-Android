package com.giaquino.sample.ui.users.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.giaquino.sample.common.util.ImageLoader;
import com.giaquino.sample.model.entity.User;
import com.giaquino.sample.ui.users.view.UserViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UsersAdapter extends RecyclerView.Adapter<UserViewHolder> {

    private LayoutInflater inflater;

    private ImageLoader loader;

    private List<User> users = new ArrayList<>();

    public UsersAdapter(Context context, ImageLoader loader) {
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.loader = loader;
    }

    @Override public int getItemViewType(int position) {
        return 1;
    }

    @Override public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return UserViewHolder.create(inflater, parent, loader);
    }

    @Override public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.bind(users.get(position));
    }

    @Override public int getItemCount() {
        return users.size();
    }

    public User getData(int position) {
        return users.get(position);
    }

    public void setData(List<User> users) {
        this.users.clear();
        this.users.addAll(users);
    }

    public void addData(List<User> users) {
        this.users.addAll(users);
    }
}
