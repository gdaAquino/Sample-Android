package com.giaquino.sample.inject.module;

import com.giaquino.sample.SampleApplication;
import com.giaquino.sample.model.db.Database;
import com.giaquino.sample.model.db.SampleDatabase;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * @author Gian Darren Azriel Aquino.
 */
@Module public class DatabaseModule {

    private final String name;

    private final int version;

    public DatabaseModule(String name, int version) {
        this.name = name;
        this.version = version;
    }

    @Provides @Singleton public Database provideDatabase(SampleApplication application) {
        return new SampleDatabase(application, name, null, version);
    }
}
