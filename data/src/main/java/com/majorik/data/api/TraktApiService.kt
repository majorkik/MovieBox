package com.majorik.data.api

import com.majorik.domain.traktModels.oauth.OAuthToken
import com.majorik.domain.traktModels.oauth.TraktBodyRequest
import com.majorik.domain.traktModels.sync.*
import retrofit2.Response
import retrofit2.http.*

interface TraktApiService {
    /*
    OAuth2
     */

    @POST("oauth/token")
    suspend fun exchangeCodeForAccessToken(
        @Query("code") code: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("grant_type") grantType: String
    ): Response<OAuthToken>

    @POST("oauth/token")
    suspend fun exchangeRefreshTokenForAccessToken(
        @Query("refresh_token") refreshToken: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUri: String,
        @Query("grant_type") grantType: String
    ): Response<OAuthToken>

    @POST("oauth/revoke")
    fun revokeAnAccessToken(
        @Query("token") token: String,
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String
    )

    /*
    type = {movies, shows, seasons, episodes}
     */

    @GET("sync/history/")
    suspend fun getHistory(
        @Header("Authorization") accessToken: String,
        @Query("type") type: String?,
        @Query("id") id: Int?,
        @Query("start_at") startAt: String?,
        @Query("end_at") endAt: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?

    ): Response<TraktHistoryResponse>

    @POST("sync/history")
    suspend fun addToHistory(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Response<TraktResultResponse>

    @POST("sync/history/remove")
    suspend fun removeFromHistory(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Response<TraktResultResponse>

    @GET("sync/watchlist")
    suspend fun getWatchlist(
        @Header("Authorization") accessToken: String,
        @Query("type") type: String?
    ): Response<List<TraktWatchlistResponse>>

    @POST("sync/watchlist")
    suspend fun addToWatchlist(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Response<TraktResultResponse>

    @POST("sync/watchlist/remove")
    suspend fun removeFromWatchlist(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Response<TraktResultResponse>

    @GET("sync/watched")
    suspend fun getWatched(
        @Header("Authorization") accessToken: String,
        @Query("type") type: String
    ): Response<TraktWatchedResponse>

    @GET("sync/rating")
    suspend fun getRating(
        @Header("Authorization") accessToken: String,
        @Query("type") type: String?,
        @Query("rating") rating: Int?
    ): Response<TraktRatingResponse>

    @POST("sync/rating")
    suspend fun addToRating(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Response<TraktResultResponse>

    @POST("sync/rating/remove")
    suspend fun removeToRating(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Response<TraktResultResponse>

    @GET("sync/last_activities")
    suspend fun getLastActivity(
        @Header("Authorization") accessToken: String
    ): Response<TraktLastActivityResponse>
}
