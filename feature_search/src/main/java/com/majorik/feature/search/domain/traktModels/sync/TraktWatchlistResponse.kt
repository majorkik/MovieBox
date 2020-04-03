package com.majorik.feature.search.domain.traktModels.sync

import com.squareup.moshi.Json

data class TraktWatchlistResponse(
    @field:Json(name = "rank") val rank: Int,
    @field:Json(name = "listed_at") val listed_at: String,
    @field:Json(name = "type") val type: String,
    @field:Json(name = "movie") val movie: TraktItem?,
    @field:Json(name = "episode") val episode: TraktItem?,
    @field:Json(name = "season") val season: TraktItem?,
    @field:Json(name = "show") val show: TraktItem?
)
