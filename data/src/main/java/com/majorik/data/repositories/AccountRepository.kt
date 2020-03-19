package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.ApiResponse
import com.majorik.domain.tmdbModels.account.AccountDetails
import com.majorik.domain.tmdbModels.movie.MovieResponse
import com.majorik.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.domain.tmdbModels.request.RequestMarkAsFavorite
import com.majorik.domain.tmdbModels.result.ResultWrapper
import com.majorik.domain.tmdbModels.tv.TVEpisodeResponse
import com.majorik.domain.tmdbModels.tv.TVResponse
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
