package com.majorik.moviebox.feature.search.data.api

import com.majorik.moviebox.feature.search.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.feature.search.domain.tmdbModels.person.PersonResponse
import com.majorik.moviebox.feature.search.domain.tmdbModels.search.MultiSearchResponse
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TVResponse
import retrofit2.http.* // ktlint-disable no-wildcard-imports

internal interface SearchApiService {
    // Search

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("include_adult") includeAdult: Boolean?,
        @Query("region") region: String?,
        @Query("year") year: Int?,
        @Query("primary_release_year") primaryReleaseYear: Int?
    ): MovieResponse

    @GET("search/tv")
    suspend fun searchTVSeries(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("first_air_date_year") firstAirDateYear: Int?
    ): TVResponse

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("include_adult") includeAdult: Boolean?
    ): MultiSearchResponse

    @GET("search/person")
    suspend fun searchPeoples(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("include_adult") includeAdult: Boolean?,
        @Query("region") region: String?
    ): PersonResponse
}
