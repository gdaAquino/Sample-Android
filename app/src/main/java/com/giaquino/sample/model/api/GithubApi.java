package com.giaquino.sample.model.api;

import com.giaquino.sample.model.entity.User;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

/**
 * @author Gian Darren Azriel Aquino.
 */
public interface GithubApi {

    @GET("users") Single<List<User>> getUsers(@Query(value = "access_token") String token,
        @Query(value = "since") int since);
}
