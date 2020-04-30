package com.majorik.moviebox.feature.collections.data.api

import com.majorik.moviebox.feature.collections.domain.tmdbModels.genre.GenreResponse
import com.majorik.moviebox.feature.collections.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TVResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CollectionApiService {

    // Movies

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): MovieResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): MovieResponse

    // TVs

    @GET("tv/popular")
    suspend fun getPopularTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): TVResponse

    @GET("tv/top_rated")
    suspend fun getTopRatedTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): TVResponse

    @GET("tv/airing_today")
    suspend fun getAiringTodayTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): TVResponse

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): TVResponse

    // Genres

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") language: String?
    ): GenreResponse

    @GET("genre/tv/list")
    suspend fun getTVGenres(
        @Query("language") language: String?
    ): GenreResponse
}
