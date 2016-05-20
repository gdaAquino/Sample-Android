package com.giaquino.sample.inject.module;

import android.database.sqlite.SQLiteOpenHelper;
import com.giaquino.sample.BuildConfig;
import com.giaquino.sample.SampleApplication;
import com.giaquino.sample.model.db.Database;
import com.giaquino.sample.model.db.SQLBriteDatabase;
import com.giaquino.sample.model.db.SampleSQLiteOpenHelper;
import com.giaquino.sample.model.db.contract.UserContract;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import rx.schedulers.Schedulers;

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

    @Provides @Singleton
    public SQLiteOpenHelper provideSQLiteOpenHelper(SampleApplication application) {
        return new SampleSQLiteOpenHelper(application, name, null, version);
    }

    @Provides @Singleton public Database provideDatabase(SQLiteOpenHelper helper) {
        return new SQLBriteDatabase(helper, Schedulers.io(), BuildConfig.DEBUG);
    }

    @Provides @Singleton public UserContract.Dao provideUserDao(Database database) {
        return new UserContract.Dao(database);
    }
}
