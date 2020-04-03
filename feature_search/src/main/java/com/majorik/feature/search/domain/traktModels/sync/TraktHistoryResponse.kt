package com.majorik.feature.search.domain.traktModels.sync

import com.squareup.moshi.Json

data class TraktHistoryResponse(
    val historyItems: List<TraktHistoryItem>
) {
    data class TraktHistoryItem(
        @field:Json(name = "id") val id: Int,
        @field:Json(name = "watched_at") val watchedAt: String,
        @field:Json(name = "action") val action: String,
        @field:Json(name = "type") val movie: String,
        @field:Json(name = "movie") val movies: List<TraktItem>?,
        @field:Json(name = "episode") val episodes: List<TraktItem>?,
        @field:Json(name = "season") val seasons: List<TraktItem>?,
        @field:Json(name = "show") val shows: List<TraktItem>?

    )
}
