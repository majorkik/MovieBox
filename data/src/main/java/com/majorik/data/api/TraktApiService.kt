package com.majorik.data.api

import com.majorik.domain.traktModels.oauth.OAuthToken
import com.majorik.domain.traktModels.oauth.TraktBodyRequest
import com.majorik.domain.traktModels.sync.*
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

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
    ): Deferred<Response<OAuthToken>>


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

    /*
    type = {movies, shows, seasons, episodes}
     */

    @GET("sync/history/")
    fun getHistory(
        @Header("Authorization") accessToken: String,
        @Query("type") type: String?,
        @Query("id") id: Int?,
        @Query("start_at") startAt: String?,
        @Query("end_at") endAt: String?,
        @Query("page") page: Int?,
        @Query("limit") limit: Int?

    ): Deferred<Response<TraktHistoryResponse>>

    @POST("sync/history")
    fun addToHistory(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Deferred<Response<TraktResultResponse>>

    @POST("sync/history/remove")
    fun removeFromHistory(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Deferred<Response<TraktResultResponse>>

    @GET("sync/watchlist")
    fun getWatchlist(
        @Header("Authorization") accessToken: String,
        @Query("type") type: String?
    ): Deferred<Response<List<TraktWatchlistResponse>>>

    @POST("sync/watchlist")
    fun addToWatchlist(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Deferred<Response<TraktResultResponse>>

    @POST("sync/watchlist/remove")
    fun removeFromWatchlist(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Deferred<Response<TraktResultResponse>>

    @GET("sync/watched")
    fun getWatched(
        @Header("Authorization") accessToken: String,
        @Query("type") type: String
    ): Deferred<Response<TraktWatchedResponse>>

    @GET("sync/rating")
    fun getRating(
        @Header("Authorization") accessToken: String,
        @Query("type") type: String?,
        @Query("rating") rating: Int?
    ): Deferred<Response<TraktRatingResponse>>

    @POST("sync/rating")
    fun addToRating(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Deferred<Response<TraktResultResponse>>

    @POST("sync/rating/remove")
    fun removeToRating(
        @Header("Authorization") accessToken: String,
        @Body traktBodyRequest: TraktBodyRequest
    ): Deferred<Response<TraktResultResponse>>

    @GET("sync/last_activities")
    fun getLastActivity(
        @Header("Authorization") accessToken: String
    ): Deferred<Response<TraktLastActivityResponse>>
}