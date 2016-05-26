package com.giaquino.sample.ui.organizations.fragment;

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
import com.giaquino.sample.common.adapter.MarginItemDecorator;
import com.giaquino.sample.common.adapter.ProgressBarAdapterDecorator;
import com.giaquino.sample.common.app.BaseFragment;
import com.giaquino.sample.model.OrganizationModel;
import com.giaquino.sample.model.entity.Organization;
import com.giaquino.sample.model.image.ImageLoader;
import com.giaquino.sample.ui.organizations.adapter.OrganizationAdapter;
import com.giaquino.sample.ui.organizations.presenter.OrganizationsPresenter;
import com.giaquino.sample.ui.organizations.view.OrganizationViewHolder;
import com.giaquino.sample.ui.organizations.view.OrganizationsView;
import dagger.Module;
import dagger.Provides;
import dagger.Subcomponent;
import java.util.List;
import javax.inject.Inject;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/24/16
 */
public class OrganizationsFragment extends BaseFragment implements OrganizationsView {

    @BindView(R.id.smp_view_list_swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.smp_view_list_recycler_view) RecyclerView recyclerView;
    @Inject OrganizationsPresenter presenter;
    @Inject ImageLoader imageLoader;
    private LinearLayoutManager layoutManager;
    private ProgressBarAdapterDecorator<Organization, OrganizationViewHolder, OrganizationAdapter> adapter;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smp_view_list, container, false);
    }

    @Override public void initialize() {
        SampleApplication.get(getContext())
            .getApplicationComponent()
            .plus(new OrganizationsFragmentModule())
            .inject(this);
        layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new ProgressBarAdapterDecorator<>(getContext(),
            new OrganizationAdapter(getContext(), imageLoader));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new MarginItemDecorator(
            (int) getResources().getDimension(R.dimen.smp_list_vertical_margin),
            (int) getResources().getDimension(R.dimen.smp_list_horizontal_margin)));
        presenter.bindView(this);
        presenter.loadOrganizations(0);
    }

    @Override public void setOrganizations(List<Organization> organizations) {
        runOnUIThread(() -> {
            adapter.setData(organizations);
            adapter.notifyDataSetChanged();
        });
    }

    @Override public void showError(String message) {
        runOnUIThread(() -> Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show());
    }

    @Subcomponent(modules = OrganizationsFragmentModule.class)
    public interface OrganizationsFragmentComponent {
        void inject(OrganizationsFragment fragment);
    }

    @Module public static class OrganizationsFragmentModule {
        @Provides
        public OrganizationsPresenter provideOrganizationsPresenter(OrganizationModel model) {
            return new OrganizationsPresenter(model);
        }
    }
}
