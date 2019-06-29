package com.majorik.data.api

import com.majorik.domain.models.*
import com.majorik.domain.models.account.AccountDetails
import com.majorik.domain.models.account.AccountStates
import com.majorik.domain.models.genre.GenreResponse
import com.majorik.domain.models.image.PersonImagesResponse
import com.majorik.domain.models.image.PersonPostersResponse
import com.majorik.domain.models.movie.MovieDetails
import com.majorik.domain.models.movie.MovieResponse
import com.majorik.domain.models.movie.MovieSortBy
import com.majorik.domain.models.person.PersonDetails
import com.majorik.domain.models.person.PersonResponse
import com.majorik.domain.models.request.RequestAddToWatchlist
import com.majorik.domain.models.request.RequestMarkAsFavorite
import com.majorik.domain.models.request.RequestSession
import com.majorik.domain.models.request.RequestToken
import com.majorik.domain.models.tv.TVDetails
import com.majorik.domain.models.tv.TVEpisodeResponse
import com.majorik.domain.models.tv.TVResponse
import com.majorik.domain.models.tv.TVSeasonDetails
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface TmdbApiService {

    //Movies
    @GET("movie/{movie_id}")
    fun getMovieById(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?,
        @Query("include_image_language") imageLanguages: String?
    ): Deferred<Response<MovieDetails>>

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): Deferred<Response<MovieResponse>>

    @GET("movie/top_rated")
    fun getTopRatedMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): Deferred<Response<MovieResponse>>

    @GET("movie/{movie_id}/account_states")
    fun getAccountStatesForMovie(
        @Path("movie_id") movieId: Int,
        @Query("session_id") sessionId: String,
        @Query("guest_session_id") guestSessionId: String?
    ): Deferred<Response<AccountStates>>

    @GET("movie/upcoming")
    fun getUpcomingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): Deferred<Response<MovieResponse>>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("region") region: String?
    ): Deferred<Response<MovieResponse>>

    //TVs
    @GET("tv/{tv_id}")
    fun getTVById(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?,
        @Query("include_image_language") imageLanguages: String?
    ): Deferred<Response<TVDetails>>

    @GET("tv/popular")
    fun getPopularTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Deferred<Response<TVResponse>>


    @GET("tv/top_rated")
    fun getTopRatedTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Deferred<Response<TVResponse>>

    @GET("tv/{tv_id}/account_states")
    fun getAccountStatesForTV(
        @Path("tv_id") tvId: Int,
        @Query("language") language: String?,
        @Query("guest_session_id") guestSessionId: String?,
        @Query("session_id") sessionId: String
    ): Deferred<Response<AccountStates>>

    @GET("tv/airing_today")
    fun getAiringTodayTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Deferred<Response<TVResponse>>

    @GET("tv/on_the_air")
    fun getOnTheAirTVs(
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Deferred<Response<TVResponse>>

    //Persons
    @GET("person/{person_id}")
    fun getPersonById(
        @Path("person_id") personId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?
    ): Deferred<Response<PersonDetails>>

    @GET("person/{person_id}/tagged_images")
    fun getPersonTaggedImages(
        @Path("person_id") personId: Int,
        @Query("language") language: String?,
        @Query("page") page: Int?
    ): Deferred<Response<PersonImagesResponse>>

    @GET("person/{person_id}/images")
    fun getPersonPosters(
        @Path("person_id") personId: Int
    ): Deferred<Response<PersonPostersResponse>>

    //Season & Episode
    @GET("tv/{tv_id}/season/{season_number}")
    fun getSeasonDetails(
        @Path("tv_id") tvId: Int,
        @Path("season_number") seasonNumber: Int,
        @Query("language") language: String?,
        @Query("append_to_response") appendToResponse: String?
    ): Deferred<Response<TVSeasonDetails>>

    //Genres
    @GET("genre/movie/list")
    fun getMovieGenres(
        @Query("language") language: String?
    ): Deferred<Response<GenreResponse>>

    @GET("genre/tv/list")
    fun getTVGenres(
        @Query("language") language: String?
    ): Deferred<Response<GenreResponse>>

    //Account
    @GET("account")
    fun getAccountDetails(
        @Query("session_id") sessionId: String
    ): Deferred<Response<AccountDetails>>

    @GET("account/{account_id}/favorite/movies")
    fun getFavoriteMovies(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Deferred<Response<MovieResponse>>

    @GET("account/{account_id}/favorite/tv")
    fun getFavoriteTVs(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Deferred<Response<TVResponse>>

    @POST("account/{account_id}/favorite")
    fun markIsFavorite(
        @Body requestMarkAsFavorite: RequestMarkAsFavorite,
        @Query("session_id") sessionId: String
    ): Deferred<Response<ResponseApi>>

    @GET("account/{account_id}/rated/movies")
    fun getRatedMovies(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Deferred<Response<MovieResponse>>

    @GET("account/{account_id}/rated/tv")
    fun getRatedTVs(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Deferred<Response<TVResponse>>

    @GET("account/{account_id}/rated/tv/episodes")
    fun getRatedEpisodes(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Deferred<Response<TVEpisodeResponse>>

    @GET("account/{account_id}/watchlist/movies")
    fun getWatchlistMovies(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Deferred<Response<MovieResponse>>

    @GET("account/{account_id}/watchlist/tv")
    fun getWatchlistTVs(
        @Query("language") language: String?,
        @Query("session_id") sessionId: String,
        @Query("sort_by") sortBy: String?,
        @Query("page") page: Int?
    ): Deferred<Response<TVResponse>>

    @POST("account/{account_id}/watchlist")
    fun addToWatchlist(
        @Body requestAddToWatchlist: RequestAddToWatchlist,
        @Query("session_id") sessionId: String
    ): Deferred<Response<ResponseApi>>


    //Search
    @GET("search/movie")
    fun searchMovies(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("include_adult") includeAdult: Boolean?,
        @Query("region") region: String?,
        @Query("year") year: Int?,
        @Query("primary_release_year") primaryReleaseYear: Int?
    ): Deferred<Response<MovieResponse>>

    @GET("search/tv")
    fun searchTVSeries(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("first_air_date_year") firstAirDateYear: Int?
    ): Deferred<Response<TVResponse>>

    @GET("search/multi")
    fun multiSearch(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("include_adult") includeAdult: Boolean?
    ): Deferred<Response<MultiSearchResponse>>

    @GET("search/person")
    fun searchPeoples(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") page: Int?,
        @Query("include_adult") includeAdult: Boolean?,
        @Query("region") region: String?
    ): Deferred<Response<PersonResponse>>

    //Auth
    @GET("authentication/token/new")
    fun getRequestToken(): Deferred<Response<RequestTokenResponse>>

    @POST("authentication/session/new")
    fun createSession(
        @Body requestToken: RequestToken
    ): Deferred<Response<ResponseSession>>

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    fun deleteSession(
        @Body sessionIdModel: RequestSession
    ): Deferred<Response<ResponseSession>>
}