package com.giaquino.sample.ui.drawer.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.giaquino.sample.R;
import com.giaquino.sample.common.app.BaseFragment;
import com.giaquino.sample.ui.organizations.fragment.OrganizationsFragment;
import com.giaquino.sample.ui.users.fragment.UsersFragment;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/26/16
 */
public class DrawerFragment extends BaseFragment {

    @BindView(R.id.smp_fragment_drawer_text_view_users) TextView textViewUsers;
    @BindView(R.id.smp_fragment_drawer_text_view_organizations) TextView textViewOrganizations;

    DrawerLayout drawerLayout;

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.smp_fragment_drawer, container, false);
    }

    @Override protected void initialize() {
    }

    @Override public void onStart() {
        super.onStart();
        drawerLayout = ButterKnife.findById(getActivity(), R.id.smp_activity_main_drawer_layout);
    }

    @OnClick(R.id.smp_fragment_drawer_text_view_users) public void onUsersClick() {
        getActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.smp_container, new UsersFragment())
            .commit();
        drawerLayout.closeDrawers();
    }

    @OnClick(R.id.smp_fragment_drawer_text_view_organizations) public void onOrganizationsClick() {
        getActivity().getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.smp_container, new OrganizationsFragment())
            .commit();
        drawerLayout.closeDrawers();
    }
}
