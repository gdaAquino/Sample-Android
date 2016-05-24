package com.giaquino.sample.ui.organizations.presenter;

import com.giaquino.sample.common.app.Presenter;
import com.giaquino.sample.model.OrganizationModel;
import com.giaquino.sample.ui.organizations.view.OrganizationsView;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/24/16
 */
public class OrganizationsPresenter extends Presenter<OrganizationsView> {

    private OrganizationModel organizationModel;

    public OrganizationsPresenter(OrganizationModel organizationModel) {
        this.organizationModel = organizationModel;
    }

    public void loadOrganizations(int since) {
        organizationModel.loadOrganizations(since);
    }

    @Override public void onBindView() {
        addSubscriptionToUnsubscribe(organizationModel.organizations().subscribe(organizations -> {
            view().setOrganizations(organizations);
        }));
        addSubscriptionToUnsubscribe(organizationModel.errors().subscribe(throwable -> {
            view().showError(throwable.toString());
        }));
    }
}
