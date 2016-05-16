package com.giaquino.sample.module;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Collections;
import java.util.List;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

/**
 * @author Gian Darren Azriel Aquino.
 */
@Module
public class OkHttpInterceptorsModule {

    public static final String INTERCEPTOR = "interceptor";

    public static final String NETWORK_INTERCEPTOR = "network_interceptor";

    @Provides @Singleton @Named(INTERCEPTOR)
    public List<Interceptor> provideOkHttpInterceptor() {
        return Collections.singletonList((Interceptor) new HttpLoggingInterceptor(
            new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Timber.tag("OkHttp").d(message);
                }
            })
            .setLevel(HttpLoggingInterceptor.Level.BASIC));
    }

    @Provides @Singleton @Named(NETWORK_INTERCEPTOR)
    public List<Interceptor> provideOkHttpNetworkInterceptor() {
        return Collections.singletonList((Interceptor) new StethoInterceptor());
    }
}
