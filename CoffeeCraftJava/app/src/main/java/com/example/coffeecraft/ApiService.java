package com.example.coffeecraft;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("login/access-token")
    Call<TokenResponse> getAccessToken(@Body LoginRequest loginRequest);

    // Define other API endpoints and methods here
}
