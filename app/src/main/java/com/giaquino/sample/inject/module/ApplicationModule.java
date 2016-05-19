package com.giaquino.sample.inject.module;

import android.os.Handler;
import android.os.Looper;
import com.giaquino.sample.SampleApplication;
import com.giaquino.sample.common.concurrent.BackgroundExecutor;
import com.giaquino.sample.common.util.ImageLoader;
import com.giaquino.sample.common.util.PicassoImageLoader;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import java.util.concurrent.Executor;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;

/**
 * @author Gian Darren Azriel Aquino.
 */
@Module public class ApplicationModule {

    private SampleApplication application;

    public ApplicationModule(SampleApplication application) {
        this.application = application;
    }

    @Provides @Singleton public SampleApplication provideApplication() {
        return application;
    }

    @Provides @Singleton
    public ImageLoader provideImageLoader(SampleApplication application, OkHttpClient client) {
        Picasso picasso =
            new Picasso.Builder(application).downloader(new OkHttp3Downloader(client)).build();
        return new PicassoImageLoader(picasso);
    }

    @Provides @Singleton public Executor provideBackgroundExecutor() {
        return new BackgroundExecutor(4);
    }

    @Provides @Singleton public Handler provideMainThreadHandler() {
        return new Handler(Looper.getMainLooper());
    }
}
