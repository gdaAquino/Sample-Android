package com.giaquino.sample.inject.module;

import dagger.Module;
import dagger.Provides;
import java.util.List;
import javax.inject.Named;
import javax.inject.Singleton;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

import static com.giaquino.sample.module.OkHttpInterceptorsModule.INTERCEPTOR;
import static com.giaquino.sample.module.OkHttpInterceptorsModule.NETWORK_INTERCEPTOR;

/**
 * @author Gian Darren Azriel Aquino.
 */
@Module public class NetworkModule {

    @Provides @Singleton
    public OkHttpClient provideOkHttpClient(@Named(INTERCEPTOR) List<Interceptor> interceptors,
        @Named(NETWORK_INTERCEPTOR) List<Interceptor> networkInterceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        for (Interceptor i : interceptors) {
            builder.addInterceptor(i);
        }
        for (Interceptor i : networkInterceptors) {
            builder.addInterceptor(i);
        }
        return builder.build();
    }
}
