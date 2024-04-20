package com.example.coffeecraft;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;

public interface ApiService {

    // @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("login/access-token") // Note: No leading slash here
    @FormUrlEncoded
    Call<TokenResponse> getAccessToken(@Field("username") String username, @Field("password") String password);

    // Define other API endpoints and methods here
}
