package com.majorik.data.api

import com.majorik.domain.tmdbModels.ApiResponse
import com.majorik.domain.tmdbModels.account.AccountDetails
import com.majorik.domain.tmdbModels.account.AccountStates
import com.majorik.domain.tmdbModels.auth.RequestTokenResponse
import com.majorik.domain.tmdbModels.auth.ResponseSession
import com.majorik.domain.tmdbModels.genre.GenreResponse
import com.majorik.domain.tmdbModels.image.ImagesResponse
import com.majorik.domain.tmdbModels.image.PersonPostersResponse
import com.majorik.domain.tmdbModels.movie.MovieDetails
import com.majorik.domain.tmdbModels.movie.MovieResponse
import com.majorik.domain.tmdbModels.person.PersonDetails
import com.majorik.domain.tmdbModels.person.PersonResponse
import com.majorik.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.domain.tmdbModels.request.RequestMarkAsFavorite
import com.majorik.domain.tmdbModels.request.RequestSession
import com.majorik.domain.tmdbModels.request.RequestToken
import com.majorik.domain.tmdbModels.search.MultiSearchResponse
import com.majorik.domain.tmdbModels.tv.TVDetails
import com.majorik.domain.tmdbModels.tv.TVEpisodeResponse
import com.majorik.domain.tmdbModels.tv.TVResponse
import com.majorik.domain.tmdbModels.tv.TVSeasonDetails
import retrofit2.Response
import retrofit2.http.*

interface TmdbApiService {

    // Movies

    @GET("movie/{movie_id}")
    suspend fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?,
        @Query("include_image_language") imageLanguages: String?
    ): Response<MovieDetails>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): Response<MovieResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): Response<MovieResponse>

    @GET("movie/{movie_id}/account_states")
    suspend fun getAccountStatesForMovie(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
        @Query("guest_session_id") guestSessionId: String?
    ): Response<AccountStates>

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): Response<MovieResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): Response<MovieResponse>

    // TVs

    @GET("tv/{tv_id}")
    suspend fun getTVById(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?,
        @Query("include_image_language") imageLanguages: String?
    ): Response<TVDetails>

    @GET("tv/popular")
    suspend fun getPopularTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Response<TVResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Response<TVResponse>

    @GET("tv/{tv_id}/account_states")
    suspend fun getAccountStatesForTV(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String?,
        @Query("guest_session_id") guestSessionId: String?,
        @Query("session_id") sessionId: String
    ): Response<AccountStates>

    @GET("tv/airing_today")
    suspend fun getAiringTodayTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Response<TVResponse>

    @GET("tv/on_the_air")
    suspend fun getOnTheAirTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Response<TVResponse>

    // Persons

    @GET("person/{person_id}")
    suspend fun getPersonById(
        @Path("person_id") personId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?
    ): Response<PersonDetails>

    @GET("person/{person_id}/tagged_images")
    suspend fun getPersonTaggedImages(
        @Path("person_id") personId: Int,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Response<ImagesResponse>

    @GET("person/{person_id}/images")
    suspend fun getPersonPosters(
        @Path("person_id") personId: Int
    ): Response<PersonPostersResponse>

    // Season & Episode

    @GET("tv/{tv_id}/season/{season_number}")
    suspend fun getSeasonDetails(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?
    ): Response<TVSeasonDetails>

    // Genres

    @GET("genre/movie/list")
    suspend fun getMovieGenres(
        @Query("language") language: String?
    ): Response<GenreResponse>

    @GET("genre/tv/list")
    suspend fun getTVGenres(
        @Query("language") language: String?
    ): Response<GenreResponse>

    // Account

    @GET("account")
    suspend fun getAccountDetails(
        @Query("session_id") sessionId: String
    ): Response<AccountDetails>

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Response<MovieResponse>

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavoriteTVs(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Response<TVResponse>

    @POST("account/{account_id}/favorite")
    suspend fun markIsFavorite(
        @Body requestMarkAsFavorite: RequestMarkAsFavorite,
        @Query("session_id") sessionId: String
    ): Response<ApiResponse>

    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovies(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Response<MovieResponse>

    @GET("account/{account_id}/rated/tv")
    suspend fun getRatedTVs(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Response<TVResponse>

    @GET("account/{account_id}/rated/tv/episodes")
    suspend fun getRatedEpisodes(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Response<TVEpisodeResponse>

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchlistMovies(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Response<MovieResponse>

    @GET("account/{account_id}/watchlist/tv")
    suspend fun getWatchlistTVs(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Response<TVResponse>

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Body requestAddToWatchlist: RequestAddToWatchlist,
        @Query("session_id") sessionId: String
    ): Response<ApiResponse>

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
    ): Response<MovieResponse>

    @GET("search/tv")
    suspend fun searchTVSeries(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("first_air_date_year") firstAirDateYear: Int?
    ): Response<TVResponse>

    @GET("search/multi")
    suspend fun multiSearch(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("include_adult") includeAdult: Boolean?
    ): Response<MultiSearchResponse>

    @GET("search/person")
    suspend fun searchPeoples(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("include_adult") includeAdult: Boolean?,
        @Query("region") region: String?
    ): Response<PersonResponse>

    // Auth

    @GET("authentication/token/new")
    suspend fun getRequestToken(): Response<RequestTokenResponse>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Body requestToken: RequestToken
    ): Response<ResponseSession>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Body sessionIdModel: RequestSession
    ): Response<ResponseSession>


    @GET("person/popular")
    suspend fun getPopularPeoples(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Response<PersonResponse>

    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMovies(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("page") page: Int?,
        @Query("language") language: String?
    ): Response<MovieResponse>

    @GET("trending/{media_type}/{time_window}")
    fun getTrendingTVs(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("language") language: String?
    ): Response<TVResponse>

    @GET("trending/{media_type}/{time_window}")
    fun getTrendingPeoples(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("language") language: String?
    ): Response<PersonResponse>
}
