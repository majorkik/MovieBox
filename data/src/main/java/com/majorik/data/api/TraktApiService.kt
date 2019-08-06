package com.majorik.data.api

import com.majorik.domain.traktModels.GrantType
import com.majorik.domain.traktModels.OAuthToken
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TraktApiService {
    /*
    OAuth2
     */
    @POST("oauth/token")
    fun exchangeCodeForAccessToken(
        @Query("code") code: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("grant_type") grantType: String
    ) : Deferred<Response<OAuthToken>>


    @POST("oauth/token")
    fun exchangeRefreshTokenForAccessToken(
        @Query("refresh_token") refreshToken: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("grant_type") grantType: String
    ): Deferred<Response<OAuthToken>>

    @POST("oauth/revoke")
    fun revokeAnAccessToken(
        @Query("token") token: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    )
}