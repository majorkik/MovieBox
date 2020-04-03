package com.majorik.feature.details.presentation

import com.majorik.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.feature.details.domain.tmdbModels.genre.GenreResponse
import com.majorik.feature.details.domain.tmdbModels.movie.MovieDetails
import com.majorik.feature.details.domain.tmdbModels.movie.MovieResponse
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MovieRepository(private val api: TmdbApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getMovieById(
        movieId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ): ResultWrapper<MovieDetails> {
        return safeApiCall(dispatcher) {
            api.getMovieById(movieId, language, appendToResponse, imageLanguages)
        }
    }

    suspend fun getPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) { api.getPopularMovies(language, page, region) }
    }

    suspend fun getTopRatedMovies(
        language: String?,
        page: Int?,
        region: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) { api.getTopRatedMovies(language, page, region) }
    }

    suspend fun getMovieGenres(language: String?): ResultWrapper<GenreResponse> {
        return safeApiCall(dispatcher) { api.getMovieGenres(language) }
    }

    suspend fun getAccountStatesForMovie(
        movieId: Int,
        sessionId: String,
        guestSessionId: String?
    ): ResultWrapper<AccountStates> {
        return safeApiCall(dispatcher) {
            api.getAccountStatesForMovie(movieId, sessionId, guestSessionId)
        }
    }

    suspend fun getUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) {
            api.getUpcomingMovies(language, page, region)
        }
    }

    suspend fun getNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) {
            api.getNowPlayingMovies(language, page, region)
        }
    }
}
