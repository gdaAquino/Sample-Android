package com.giaquino.sample.ui.organizations.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import com.giaquino.sample.R;
import com.giaquino.sample.common.app.BaseFragment;
import com.giaquino.sample.model.entity.Organization;
import com.giaquino.sample.ui.organizations.view.OrganizationsView;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/24/16
 */
public class OrganizationsFragment extends BaseFragment implements OrganizationsView {

    @BindView(R.id.smp_view_list_swipe_refresh_layout) SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.smp_view_list_recycler_view) RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;


    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smp_view_list, container, false);
    }

    @Override public void initialize() {
    }

    @Override public void setOrganizations(List<Organization> organizations) {

    }

    @Override public void showError(String message) {

    }
}
