package com.giaquino.sample.model.api;

import com.giaquino.sample.model.entity.Organization;
import com.giaquino.sample.model.entity.User;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Single;

/**
 * @author Gian Darren Azriel Aquino.
 */
public interface GithubApi {

    String GITHUB_TOKEN = "ef085af14ec8e343d27c8a329d66a5ae15fc6b92";

    @GET("users") Single<List<User>> getUsers(@Query(value = "access_token") String token,
        @Query(value = "since") int since);

    @GET("organizations") Single<List<Organization>> getOrganizations(
        @Query(value = "access_token") String token, @Query(value = "since") int since);
}
