package com.majorik.data.repositories

import com.majorik.data.api.YouTubeApiService
import com.majorik.domain.tmdbModels.result.ResultWrapper
import com.majorik.domain.youtubeModels.SearchResponse
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
