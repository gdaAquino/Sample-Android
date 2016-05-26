package com.giaquino.sample.inject.module;

import com.giaquino.sample.model.OrganizationModel;
import com.giaquino.sample.model.UserModel;
import com.giaquino.sample.model.api.GithubApi;
import com.giaquino.sample.model.db.contract.UserContract;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * @author Gian Darren Azriel Aquino.
 */
@Module public class ModelModule {

    @Provides @Singleton public UserModel provideUserModel(UserContract.Dao dao, GithubApi api) {
        return new UserModel(dao, api);
    }

    @Provides @Singleton public OrganizationModel provideOrganizationModel(GithubApi api) {
        return new OrganizationModel(api);
    }
}
