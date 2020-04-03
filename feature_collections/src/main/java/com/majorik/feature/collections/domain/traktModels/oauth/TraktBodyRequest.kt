package com.majorik.feature.collections.domain.traktModels.oauth

import com.majorik.feature.collections.domain.traktModels.sync.TraktItem
import com.squareup.moshi.Json

data class TraktBodyRequest(
    @field:Json(name = "movie") val movies: List<TraktItem>?,
    @field:Json(name = "episode") val episodes: List<TraktItem>?,
    @field:Json(name = "season") val seasons: List<TraktItem>?,
    @field:Json(name = "show") val shows: List<TraktItem>?
)
