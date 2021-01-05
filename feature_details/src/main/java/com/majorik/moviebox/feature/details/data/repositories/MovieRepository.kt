package com.majorik.moviebox.feature.details.data.repositories

import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieDetails
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.details.data.api.DetailsRetrofitService
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MovieRepository(private val api: DetailsRetrofitService) : BaseRepository() {

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

    suspend fun getAccountStatesForMovie(
        movieId: Int,
        sessionId: String,
        guestSessionId: String?
    ): ResultWrapper<AccountStates> {
        return safeApiCall(dispatcher) {
            api.getAccountStatesForMovie(movieId, sessionId, guestSessionId)
        }
    }

    suspend fun getRecommendations(
        movieId: Int,
        language: String?,
        page: Int?,
        region: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) { api.getRecommendations(movieId, language, page, region) }
    }
}
