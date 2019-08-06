package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.account.AccountStates
import com.majorik.domain.tmdbModels.genre.Genre
import com.majorik.domain.tmdbModels.movie.MovieDetails
import com.majorik.domain.tmdbModels.movie.MovieResponse

class MovieRepository(private val api: TmdbApiService) : BaseRepository() {
    suspend fun getMovieById(
        movieId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ): MovieDetails? {
        return safeApiCall(
            call = {
                api.getMovieById(movieId, language, appendToResponse, imageLanguages).await()
            },
            errorMessage = "Ошибка GET[getMovieById]\n" +
                    "(movieId = $movieId)"
        )
    }

    suspend fun getPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ): MutableList<MovieResponse.Movie>? {
        val movieResponse = safeApiCall(
            call = { api.getPopularMovies(language, page, region).await() },
            errorMessage = "Ошибка GET[getPopularMovies]"
        )

        return movieResponse?.results?.toMutableList()
    }

    suspend fun getTopRatedMovies(
        language: String?,
        page: Int?,
        region: String?
    ): MutableList<MovieResponse.Movie>? {
        val movieResponse = safeApiCall(
            call = { api.getTopRatedMovies(language, page, region).await() },
            errorMessage = "Ошибка [getTopRatedMovies]"
        )

        return movieResponse?.results?.toMutableList()
    }

    suspend fun getMovieGenres(language: String?): MutableList<Genre>? {
        val movieResponse = safeApiCall(
            call = { api.getMovieGenres(language).await() },
            errorMessage = "Ошибка GET[getMovieGenres]"
        )

        return movieResponse?.genres?.toMutableList()
    }

    suspend fun getAccountStatesForMovie(
        movieId: Int,
        sessionId: String,
        guestSessionId: String?
    ): AccountStates? {
        return safeApiCall(
            call = {
                api.getAccountStatesForMovie(movieId, sessionId, guestSessionId).await()
            },
            errorMessage = "Ошибка GET[getAccountStatesForMovie]\n" +
                    "(id = $movieId, session = $sessionId, guestSessionId = $guestSessionId)"
        )
    }

    suspend fun getUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?
    ): MutableList<MovieResponse.Movie>? {
        val movieResponse = safeApiCall(
            call = {
                api.getUpcomingMovies(language, page, region).await()
            },
            errorMessage = "Ошибка GET[getUpcomingMovies]"
        )
        return movieResponse?.results?.toMutableList()
    }

    suspend fun getNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?
    ): MutableList<MovieResponse.Movie>? {
        val movieResponse = safeApiCall(
            call = {
                api.getNowPlayingMovies(language, page, region).await()
            },
            errorMessage = "Ошибка GET[getNowPlayingMovies]"
        )
        return movieResponse?.results?.toMutableList()
    }

}