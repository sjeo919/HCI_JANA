package com.jana.overwatch;

import com.jana.overwatch.POJO.Device;
import com.jana.overwatch.POJO.OAuthClient;
import com.jana.overwatch.POJO.OAuthToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Andrew Jeong on 2016-09-25.
 */

public interface M2XService {

    @GET("/oauth/authorize")
    Call<OAuthClient> getAuthorised(
            @Query("response_type") String type,
            @Query("client_id") String id,
            @Query("redirect_uri") String uri,
            @Query("state") String state,
            @Query("scope") String scope
    );

    @POST("/oauth/token")
    Call<OAuthToken> getAccessToken(
            @Query("client_id") String id,
            @Query("client_secret") String secret,
            @Query("grant_type") String type,
            @Query("code") String code,
            @Query("redirect_uri") String uri
    );

    @GET("/devices")
    Call<List<Device>> fetchDevices(
            @Header("X-M2X-KEY") String masterKey
    );
}
