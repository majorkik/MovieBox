package com.majorik.feature.search.data.api

import com.majorik.feature.search.domain.youtubeModels.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("search/")
    suspend fun searchYouTubeVideos(
        @Query("key") key: String,
        @Query("part") part: String,
        @Query("maxResults") maxResults: Int,
        @Query("pageToken") pageToken: String?,
        @Query("order") order: String,
        @Query("channelId") channelId: String
    ): SearchResponse
}
