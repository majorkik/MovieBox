package com.majorik.moviebox.feature.navigation.data.api

import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TVResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbV4ApiService {
    @GET("/account/{account_id}/movie/favorites")
    suspend fun getFavoriteMovies(
        @Path("account_id") sessionId: String,
        @Query("language") language: String?,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): MovieResponse

    @GET("/account/{account_id}/tv/favorites")
    suspend fun getFavoriteTVs(
        @Path("account_id") sessionId: String,
        @Query("language") language: String?,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): TVResponse

    @GET("/account/{account_id}/movie/rated")
    suspend fun getRatedMovies(
        @Path("account_id") sessionId: String,
        @Query("language") language: String?,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): MovieResponse

    @GET("/account/{account_id}/tv/rated")
    suspend fun getRatedTVs(
        @Path("account_id") sessionId: String,
        @Query("language") language: String?,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): TVResponse

    @GET("/account/{account_id}/movie/watchlist")
    suspend fun getWatchlistMovies(
        @Path("account_id") sessionId: String,
        @Query("language") language: String?,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): MovieResponse

    @GET("/account/{account_id}/tv/watchlist")
    suspend fun getWatchlistTVs(
        @Path("account_id") sessionId: String,
        @Query("language") language: String?,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): TVResponse

    @GET("/account/{account_id}/movie/recommendations")
    suspend fun getMovieRecommendations(
        @Path("account_id") sessionId: String,
        @Query("language") language: String?,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): MovieResponse

    @GET("/account/{account_id}/tv/recommendations")
    suspend fun getTVRecommendations(
        @Path("account_id") sessionId: String,
        @Query("language") language: String?,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): TVResponse
}
