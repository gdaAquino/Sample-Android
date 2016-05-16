package com.giaquino.sample.ui.users.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.giaquino.sample.R;
import com.giaquino.sample.SampleApplication;
import com.giaquino.sample.common.app.BaseFragment;
import com.giaquino.sample.common.util.ImageLoader;
import com.giaquino.sample.common.widget.PaginatedOnScrollListener;
import com.giaquino.sample.model.entity.User;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.ui.users.adapter.UsersAdapter;
import com.giaquino.sample.ui.users.presenter.UsersPresenter;
import com.giaquino.sample.ui.users.view.UsersView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class UsersFragment extends BaseFragment implements UsersView {

    @Subcomponent(modules = UsersFragmentModule.class)
    public interface UsersFragmentComponent {
        void inject(UsersFragment fragment);
    }

    @Module
    public static class UsersFragmentModule {
        @Provides
        public UsersPresenter provideUserPresenter(UserModel model) {
            return new UsersPresenter(model);
        }
    }

    @Inject
    UsersPresenter presenter;

    @Inject
    ImageLoader imageLoader;

    @BindView(R.id.smp_users_fragment_recyclerview)
    RecyclerView recyclerView;

    UsersAdapter adapter;

    LinearLayoutManager manager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smp_users_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        SampleApplication.get(getContext())
                         .getApplicationComponent()
                         .plus(new UsersFragmentModule())
                         .inject(this);
        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new UsersAdapter(getContext(), imageLoader);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginatedOnScrollListener(manager) {
            @Override
            public void onNextPage(int page) {
                if (adapter.getItemCount() == 0) return;
                presenter.loadNextUsers(adapter.getData(adapter.getItemCount() -1).id());
            }
        });
        presenter.bindView(this);
        presenter.loadUsers();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.unbindView();
    }

    @Override
    public void setUsers(final List<User> users) {
        runOnUIThreadIfFragmentAlive(new Runnable() {
            @Override
            public void run() {
                adapter.setData(users);
            }
        });
    }

    @Override
    public void addUsers(final List<User> users) {
        runOnUIThreadIfFragmentAlive(new Runnable() {
            @Override
            public void run() {
                adapter.addData(users);
            }
        });
    }

    @Override
    public void showErrorMessage(final String message) {
        runOnUIThreadIfFragmentAlive(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
