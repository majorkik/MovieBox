package com.majorik.feature.search.domain.traktModels.sync

import com.squareup.moshi.Json

data class TraktRatingResponse(
    @field:Json(name = "rated_at") val ratedAt: String,
    @field:Json(name = "rating") val rating: Int,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "movie") val movies: List<TraktItem>?,
    @field:Json(name = "episode") val episodes: List<TraktItem>?,
    @field:Json(name = "season") val seasons: List<TraktItem>?,
    @field:Json(name = "show") val shows: List<TraktItem>?
)
