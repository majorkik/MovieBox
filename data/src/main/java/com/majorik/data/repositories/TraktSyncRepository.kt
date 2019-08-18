package com.majorik.data.repositories

import com.majorik.data.api.TraktApiService
import com.majorik.domain.traktModels.sync.TraktWatchlistResponse

class TraktSyncRepository(private val apiTraktService: TraktApiService) : BaseRepository() {
    suspend fun getWatchlist(
        accessToken: String,
        type: String?
    ): List<TraktWatchlistResponse>? {
        return safeApiCall(
        call = {
            apiTraktService.getWatchlist(accessToken, type).await()
        },
        errorMessage = "Ошибка GET[getWatchlist]\n" +
                "(accessToken = $accessToken)"
    )
    }
}