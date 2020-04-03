package com.majorik.feature.search.domain.traktModels.sync

import com.squareup.moshi.Json

data class TraktResultResponse(
    @field:Json(name = "added") val added: ResultAdded?,
    @field:Json(name = "deleted") val deleted: ResultAdded?,
    @field:Json(name = "not_found") val notFound: NotFoundItems
) {
    data class ResultAdded(
        @field:Json(name = "movies") val movies: Int?,
        @field:Json(name = "shows") val shows: Int?,
        @field:Json(name = "episodes") val episodes: Int?,
        @field:Json(name = "seasons") val seasons: Int?
    )

    data class NotFoundItems(
        @field:Json(name = "movie") val movies: List<TraktItem>?,
        @field:Json(name = "episode") val episodes: List<TraktItem>?,
        @field:Json(name = "season") val seasons: List<TraktItem>?,
        @field:Json(name = "show") val shows: List<TraktItem>?
    )
}
