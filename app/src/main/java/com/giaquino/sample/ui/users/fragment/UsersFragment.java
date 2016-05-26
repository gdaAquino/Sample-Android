package com.giaquino.sample.ui.users.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import com.giaquino.sample.R;
import com.giaquino.sample.SampleApplication;
import com.giaquino.sample.common.adapter.decorator.MarginItemDecorator;
import com.giaquino.sample.common.adapter.ProgressBarAdapterDecorator;
import com.giaquino.sample.common.app.BaseFragment;
import com.giaquino.sample.common.listener.DirectionalOnScrollListener;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.model.entity.User;
import com.giaquino.sample.model.image.ImageLoader;
import com.giaquino.sample.ui.users.adapter.UsersAdapter;
import com.giaquino.sample.ui.users.presenter.UsersPresenter;
import com.giaquino.sample.ui.users.viewholder.UserViewHolder;
import com.giaquino.sample.ui.users.view.UsersView;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UsersFragment extends BaseFragment implements UsersView {

    @BindView(R.id.smp_view_list_swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.smp_view_list_recycler_view) RecyclerView recyclerView;
    @Inject UsersPresenter presenter;
    @Inject ImageLoader imageLoader;
    private LinearLayoutManager layoutManager;
    private DirectionalOnScrollListener scrollListener;
    private ProgressBarAdapterDecorator<User, UserViewHolder, UsersAdapter> adapter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smp_view_list, container, false);
    }

    @Override public void initialize() {
        Context context = getContext();
        SampleApplication.get(context)
            .getApplicationComponent()
            .plus(new UsersFragmentModule())
            .inject(this);
        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        adapter = new ProgressBarAdapterDecorator<>(context, new UsersAdapter(context, imageLoader));
        scrollListener = new DirectionalOnScrollListener(layoutManager) {
            @Override
            public void onScrollDown(int firstItemIndex, int lastItemIndex, int totalItemCount) {
                if (lastItemIndex == totalItemCount - 1) { //last item
                    setNotifyDownScroll(false);
                    adapter.setRefreshing(true);
                    adapter.notifyDataSetChanged();
                    presenter.loadUsers(adapter.getData(adapter.getItemCount() - 1).id());
                }
            }
        };
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadUsers(0));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MarginItemDecorator(
            (int) getResources().getDimension(R.dimen.smp_list_vertical_margin),
            (int) getResources().getDimension(R.dimen.smp_list_horizontal_margin)));
        recyclerView.addOnScrollListener(scrollListener);
        presenter.bindView(this);
        presenter.loadUsers(0);
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        presenter.unbindView();
    }

    @Override public void setUsers(final List<User> users) {
        runOnUIThread(() -> {
            adapter.setRefreshing(false);
            adapter.setData(users);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            scrollListener.setNotifyDownScroll(true);
        });
    }

    @Override public void showError(final String message) {
        runOnUIThread(() -> {
            adapter.setRefreshing(false);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
            scrollListener.setNotifyDownScroll(true);
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        });
    }

    @Subcomponent(modules = UsersFragmentModule.class) public interface UsersFragmentComponent {
        void inject(UsersFragment fragment);
    }

    @Module public static class UsersFragmentModule {
        @Provides public UsersPresenter provideUserPresenter(UserModel model) {
            return new UsersPresenter(model);
        }
    }
}
