package com.majorik.moviebox.feature.details.domain.traktModels.sync

import com.squareup.moshi.Json

data class TraktWatchedResponse(
    @field:Json(name = "plays") val plays: Int,
    @field:Json(name = "last_watched_at") val lastWatchedAt: String,
    @field:Json(name = "last_updated_at") val lastUpdatedAt: String,
    @field:Json(name = "movie") val movies: List<TraktItem>?,
    @field:Json(name = "episode") val episodes: List<TraktItem>?,
    @field:Json(name = "season") val seasons: List<TraktItem>?,
    @field:Json(name = "show") val shows: List<TraktItem>?
)
