package com.majorik.data.repositories

import com.majorik.data.api.TraktApiService
import com.majorik.domain.tmdbModels.result.ResultWrapper
import com.majorik.domain.traktModels.sync.TraktWatchlistResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

class TraktSyncRepository(private val apiTraktService: TraktApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getWatchlist(
        accessToken: String,
        type: String?
    ): ResultWrapper<Response<List<TraktWatchlistResponse>>> {
        return safeApiCall(dispatcher) {
            apiTraktService.getWatchlist(accessToken, type)
        }
    }
}
