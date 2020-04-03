package com.majorik.feature.collections.data.repositories

import com.majorik.feature.collections.data.api.YouTubeApiService
import com.majorik.feature.collections.domain.youtubeModels.SearchResponse
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class YouTubeRepository(private val apiService: YouTubeApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun searchYouTubeVideos(
        key: String,
        part: String,
        maxResults: Int,
        pageToken: String?,
        order: String,
        channelId: String
    ): ResultWrapper<SearchResponse> {
        return safeApiCall(dispatcher) {
            apiService.searchYouTubeVideos(key, part, maxResults, pageToken, order, channelId)
        }
    }
}
