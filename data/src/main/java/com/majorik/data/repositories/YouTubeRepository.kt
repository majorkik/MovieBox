package com.majorik.data.repositories

import com.majorik.data.api.YouTubeApiService
import com.majorik.domain.youtubeModels.SearchResponse

class YouTubeRepository(private val apiService: YouTubeApiService) : BaseRepository() {
    suspend fun searchYouTubeVideos(
        key: String,
        part: String,
        maxResults: Int,
        pageToken: String?,
        order: String,
        channelId: String
    ): SearchResponse? {
        val response = safeApiCall(
            call = {
                apiService.searchYouTubeVideos(key, part, maxResults, pageToken, order, channelId)
            },
            errorMessage = ""
        )

        return response
    }
}
