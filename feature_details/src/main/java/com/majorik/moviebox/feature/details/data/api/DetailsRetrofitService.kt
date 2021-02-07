package com.majorik.moviebox.feature.details.data.api

import com.majorik.moviebox.feature.details.domain.tmdbModels.ApiResponse
import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.feature.details.domain.tmdbModels.person.PersonDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestMarkAsFavorite
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.TVDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.TVSeasonDetails
import retrofit2.http.*

interface DetailsRetrofitService {

    // Movies

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?,
        @Query("include_image_language") imageLanguages: String?
    ): MovieDetails

    @GET("movie/{movie_id}/account_states")
    suspend fun getAccountStatesForMovie(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
        @Query("guest_session_id") guestSessionId: String?
    ): AccountStates

    // TVs

    @GET("tv/{tv_id}")
    suspend fun getTVById(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?,
        @Query("include_image_language") imageLanguages: String?
    ): TVDetails

    @GET("tv/{tv_id}/account_states")
    suspend fun getAccountStatesForTV(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String?,
        @Query("guest_session_id") guestSessionId: String?,
        @Query("session_id") sessionId: String
    ): AccountStates

    // Persons

    @GET("person/{person_id}")
    suspend fun getPersonById(
        @Path("person_id") personId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?
    ): PersonDetails

    // Season & Episode

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?
    ): TVSeasonDetails

    // Account

    @POST("account/{account_id}/favorite")
    suspend fun markIsFavorite(
        @Body requestMarkAsFavorite: RequestMarkAsFavorite,
        @Query("session_id") sessionId: String
    ): ApiResponse

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Body requestAddToWatchlist: RequestAddToWatchlist,
        @Query("session_id") sessionId: String
    ): ApiResponse

    // todo после удалить

    // Movies

    @GET("movie/popular")
    suspend fun getRecommendations(
        @Query("movie_id") movieId: Int,
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): MovieResponse
}
