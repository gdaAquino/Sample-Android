package com.giaquino.sample.inject.module;

import com.giaquino.sample.model.api.GithubApi;
import com.giaquino.sample.model.db.Database;
import com.giaquino.sample.model.UserModel;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author Gian Darren Azriel Aquino.
 */
@Module
public class ModelModule {

    @Provides @Singleton
    public UserModel provideUserModel(Database database, GithubApi api) {
        return new UserModel(database, api);
    }
}
