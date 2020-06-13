package com.majorik.moviebox.feature.navigation.data.repositories

import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.navigation.data.api.TmdbApiService
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.account.AccountDetails
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.movie.MovieResponse
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TVResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AccountRepository(private val api: TmdbApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getAccountDetails(sessionId: String): ResultWrapper<AccountDetails> {
        return safeApiCall(dispatcher) { api.getAccountDetails(sessionId) }
    }

    suspend fun getFavoriteMovies(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) { api.getFavoriteMovies(language, sessionId, sortBy, page) }
    }

    suspend fun getFavoriteTVs(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) { api.getFavoriteTVs(language, sessionId, sortBy, page) }
    }

    suspend fun getWatchlistMovies(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) { api.getWatchlistMovies(language, sessionId, sortBy, page) }
    }

    suspend fun getWatchlistTVs(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) { api.getWatchlistTVs(language, sessionId, sortBy, page) }
    }
}
