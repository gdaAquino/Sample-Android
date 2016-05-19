package com.giaquino.sample.inject.module;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giaquino.sample.BuildConfig;
import com.giaquino.sample.model.api.GithubApi;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.schedulers.Schedulers;

/**
 * @author Gian Darren Azriel Aquino.
 */
@Module public class ApiModule {

    private final String baseUrl;

    public ApiModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Provides @Singleton public GithubApi provideGithubApi(OkHttpClient client) {
        return new Retrofit.Builder().baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(JacksonConverterFactory.create(
                new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    false)))
            .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
            .validateEagerly(BuildConfig.DEBUG)
            .build()
            .create(GithubApi.class);
    }
}
