package com.giaquino.sample.ui.organizations.view;

import com.giaquino.sample.model.entity.Organization;
import java.util.List;

/**
 * @author Gian Darren Azriel Aquino
 * @since 5/24/16
 */
public interface OrganizationsView {

    void setOrganizations(List<Organization> organizations);

    void showError(String message);

}
