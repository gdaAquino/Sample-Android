package com.giaquino.sample.ui.users.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindView;
import com.giaquino.sample.R;
import com.giaquino.sample.SampleApplication;
import com.giaquino.sample.common.app.BaseFragment;
import com.giaquino.sample.common.util.ImageLoader;
import com.giaquino.sample.common.widget.DirectionalOnScrollListener;
import com.giaquino.sample.common.widget.ListLoadingAdapterDecorator;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.model.entity.User;
import com.giaquino.sample.ui.users.adapter.UsersAdapter;
import com.giaquino.sample.ui.users.presenter.UsersPresenter;
import com.giaquino.sample.ui.users.view.UserViewHolder;
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

    @BindView(R.id.smp_users_fragment_recyclerview) RecyclerView recyclerView;

    @Inject UsersPresenter presenter;
    @Inject ImageLoader imageLoader;

    LinearLayoutManager manager;
    DirectionalOnScrollListener scrollListener;
    ListLoadingAdapterDecorator<User, UserViewHolder, UsersAdapter> adapter;

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smp_users_fragment, container, false);
    }

    @Override public void initialize() {
        SampleApplication.get(getContext())
            .getApplicationComponent()
            .plus(new UsersFragmentModule())
            .inject(this);
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new ListLoadingAdapterDecorator<>(getContext(),
            new UsersAdapter(getContext(), imageLoader));
        scrollListener = new DirectionalOnScrollListener(manager) {
            @Override
            public void onScrollDown(int firstItemIndex, int lastItemIndex, int totalItemCount) {
                if (lastItemIndex == totalItemCount - 1) { //last item
                    setNotifyDownScroll(false);
                    adapter.setShowLoadingIndicator(true);
                    adapter.notifyDataSetChanged();
                    presenter.loadUsers(
                        adapter.getData(adapter.getDelegateItemCount() - 1).id());
                }
            }
        };
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(scrollListener);
        presenter.bindView(this);
        presenter.loadUsers();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        presenter.unbindView();
    }

    @Override public void setUsers(final List<User> users) {
        runOnUIThreadIfFragmentAlive(() -> {
            adapter.setShowLoadingIndicator(false);
            adapter.setData(users);
            adapter.notifyDataSetChanged();
            scrollListener.setNotifyDownScroll(true);
        });
    }

    @Override public void showErrorMessage(final String message) {
        runOnUIThreadIfFragmentAlive(() -> {
            adapter.setShowLoadingIndicator(false);
            adapter.notifyDataSetChanged();
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
