package com.giaquino.sample;

import android.app.Application;
import android.content.Context;
import com.giaquino.sample.inject.component.ApplicationComponent;
import com.giaquino.sample.inject.component.DaggerApplicationComponent;
import com.giaquino.sample.inject.module.ApiModule;
import com.giaquino.sample.inject.module.ApplicationModule;
import com.giaquino.sample.inject.module.DatabaseModule;
import timber.log.Timber;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class SampleApplication extends Application {

    private ApplicationComponent applicationComponent;

    public static SampleApplication get(Context context) {
        return (SampleApplication) context.getApplicationContext();
    }

    @Override public void onCreate() {
        super.onCreate();
        applicationComponent = prepareApplicationComponent();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    /**
     * Initialize {@link ApplicationComponent} and its {@link dagger.Module}.
     *
     * @return {@link ApplicationComponent}
     */
    private ApplicationComponent prepareApplicationComponent() {
        return DaggerApplicationComponent.builder()
            .apiModule(new ApiModule("https://api.github.com/"))
            .applicationModule(new ApplicationModule(this))
            .databaseModule(new DatabaseModule("Sample.db", 1))
            .build();
    }
}
