package com.majorik.moviebox.feature.navigation.data.repositories

import com.majorik.moviebox.feature.navigation.data.api.YouTubeApiService
import com.majorik.moviebox.feature.navigation.domain.youtubeModels.SearchResponse
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
