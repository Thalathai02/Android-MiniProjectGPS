package com.example.demo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.POST;

public interface APiservice {

    @GET("/api/locations")
    Call<List<JsonItem>> locations();

    @GET("/api/users")
    Call<List<UserJson>> user();

    @POST("/api/users")
    Call<PostUser> Postuser(@Body PostUser postUser);

    @POST("/api/locations")
    Call<postLocation> PostLocation(@Body postLocation postLocations);
}
