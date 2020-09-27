package com.majorik.moviebox.feature.details.data.repositories

import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.details.data.api.DetailsRetrofitService
import com.majorik.moviebox.feature.details.domain.tmdbModels.ApiResponse
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestAddToWatchlist
import com.majorik.moviebox.feature.details.domain.tmdbModels.request.RequestMarkAsFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AccountRepository(private val api: DetailsRetrofitService) : BaseRepository() {
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun markIsFavorite(
        requestMarkAsFavorite: RequestMarkAsFavorite,
        sessionId: String
    ): ResultWrapper<ApiResponse> {
        return safeApiCall(dispatcher) {
            api.markIsFavorite(requestMarkAsFavorite, sessionId)
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
