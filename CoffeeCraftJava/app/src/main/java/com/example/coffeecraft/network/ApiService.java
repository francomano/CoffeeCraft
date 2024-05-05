package com.example.coffeecraft.network;



import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import com.example.coffeecraft.model.TokenResponse;
import com.example.coffeecraft.model.UserOut;
import com.example.coffeecraft.model.UserCreateOpen;
import com.example.coffeecraft.model.BuyCoffeeRequest;
import com.example.coffeecraft.model.PurchaseResponse;

import java.util.List;

import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiService {

    // @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("login/access-token") // Note: No leading slash here
    @FormUrlEncoded
    Call<TokenResponse> getAccessToken(@Field("username") String username, @Field("password") String password);

    // Define other API endpoints and methods here


    @POST("users/open")
    Call<UserOut> createUserOpen(@Body UserCreateOpen user);

    @GET("users/me")
    Call<UserOut> getCurrentUser(@Header("Authorization") String authToken);


    @GET("coffee")
    Call<List<String>> suggestCoffee(
            @Header("Authorization") String authToken,
            @Query("mood") String mood,
            @Query("sugar") Integer sugar);

    @POST("coffee/buy")
    Call<PurchaseResponse> buyCoffee(
            @Header("Authorization") String authToken,
            @Body BuyCoffeeRequest request
    );
}
