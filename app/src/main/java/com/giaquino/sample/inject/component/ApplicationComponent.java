package com.giaquino.sample.inject.component;

import com.giaquino.sample.inject.module.ApiModule;
import com.giaquino.sample.inject.module.ApplicationModule;
import com.giaquino.sample.inject.module.DatabaseModule;
import com.giaquino.sample.inject.module.ModelModule;
import com.giaquino.sample.inject.module.NetworkModule;
import com.giaquino.sample.module.OkHttpInterceptorsModule;
import com.giaquino.sample.ui.organizations.fragment.OrganizationsFragment;
import com.giaquino.sample.ui.users.fragment.UsersFragment;
import dagger.Component;
import javax.inject.Singleton;

/**
 * @author Gian Darren Azriel Aquino.
 */
@Singleton @Component(modules = {
    ApiModule.class, ApplicationModule.class, DatabaseModule.class, NetworkModule.class,
    ModelModule.class, OkHttpInterceptorsModule.class
}) public interface ApplicationComponent {

    UsersFragment.UsersFragmentComponent plus(UsersFragment.UsersFragmentModule module);

    OrganizationsFragment.OrganizationsFragmentComponent plus(
        OrganizationsFragment.OrganizationsFragmentModule module);
}
