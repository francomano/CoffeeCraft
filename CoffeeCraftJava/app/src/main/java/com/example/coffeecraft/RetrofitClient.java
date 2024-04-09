package com.example.coffeecraft;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.lang.reflect.Type;
import android.util.Log;
public class RetrofitClient {

    private static Retrofit retrofit = null;

    private RetrofitClient() {
    }

    public static ApiService getClient(String baseUrl) {
        if (retrofit == null) {
            try {
                // Create a trust manager that does not validate certificate chains
                final TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            @Override
                            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                            }

                            @Override
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return new java.security.cert.X509Certificate[]{};
                            }
                        }
                };

                // Install the all-trusting trust manager
                final SSLContext sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

                // Create an OkHttpClient with the above SSL context
                OkHttpClient.Builder builder = new OkHttpClient.Builder()
                        .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustAllCerts[0])
                        .hostnameVerifier((hostname, session) -> true); // Bypass hostname verification

                // Add logging interceptor
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);

                // Create custom Gson instance
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(TokenResponse.class, new CustomDeserializer())
                        .setLenient()
                        .create();

                retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(builder.build())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

            } catch (NoSuchAlgorithmException | KeyManagementException e) {
                e.printStackTrace();
            }
        }
        return retrofit.create(ApiService.class);
    }


    // Custom Deserializer
    public static class CustomDeserializer implements JsonDeserializer<TokenResponse> {
        @Override
        public TokenResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String rawJson = json.toString();
            Log.d("RawResponse", rawJson);  // Log the raw response to see its content
            if (json.isJsonObject()) {
                // If the response is a JSON object, proceed with the default deserialization
                return new Gson().fromJson(json, typeOfT);
            } else if (json.isJsonPrimitive()) {
                // If the response is a JSON primitive (like a string), handle it here
                String errorMessage = json.getAsString();
                throw new JsonParseException(errorMessage);
            } else {
                throw new JsonParseException("Unexpected JSON type: " + json.getClass());
            }
        }
    }

}

