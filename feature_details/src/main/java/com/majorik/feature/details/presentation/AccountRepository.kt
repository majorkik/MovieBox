package com.majorik.feature.details.presentation

import com.majorik.feature.details.domain.tmdbModels.ApiResponse
import com.majorik.feature.details.domain.tmdbModels.account.AccountDetails
import com.majorik.feature.details.domain.tmdbModels.movie.MovieResponse
import com.majorik.feature.details.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.feature.details.domain.tmdbModels.request.RequestMarkAsFavorite
import com.majorik.feature.details.domain.tmdbModels.tv.TVEpisodeResponse
import com.majorik.feature.details.domain.tmdbModels.tv.TVResponse
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AccountRepository(private val api: TmdbApiService) : BaseRepository() {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getAccountDetails(sessionId: String): ResultWrapper<AccountDetails>? {
        return safeApiCall(dispatcher) {
            api.getAccountDetails(sessionId)
        }
    }

    suspend fun getFavoriteMovies(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) {
            api.getFavoriteMovies(language, sessionId, sortBy, page)
        }
    }

    suspend fun getFavoriteTVs(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) {
            api.getFavoriteTVs(language, sessionId, sortBy, page)
        }
    }

    suspend fun markIsFavorite(
        requestMarkAsFavorite: RequestMarkAsFavorite,
        sessionId: String
    ): ResultWrapper<ApiResponse> {
        return safeApiCall(dispatcher) {
            api.markIsFavorite(requestMarkAsFavorite, sessionId)
        }
    }

    suspend fun getRatedMovies(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) {
            api.getRatedMovies(language, sessionId, sortBy, page)
        }
    }

    suspend fun getRatedTVs(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) {
            api.getRatedTVs(language, sessionId, sortBy, page)
        }
    }

    suspend fun getRatedEpisodes(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<TVEpisodeResponse> {
        return safeApiCall(dispatcher) {
            api.getRatedEpisodes(language, sessionId, sortBy, page)
        }
    }

    suspend fun getWatchlistMovies(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) {
            api.getWatchlistMovies(language, sessionId, sortBy, page)
        }
    }

    suspend fun getWatchlistTVs(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) {
            api.getWatchlistTVs(language, sessionId, sortBy, page)
        }
    }

    suspend fun addToWatchlist(
        requestAddToWatchlist: RequestAddToWatchlist,
        sessionId: String
    ): ResultWrapper<ApiResponse> {
        return safeApiCall(dispatcher) {
            api.addToWatchlist(requestAddToWatchlist, sessionId)
        }
    }
}
