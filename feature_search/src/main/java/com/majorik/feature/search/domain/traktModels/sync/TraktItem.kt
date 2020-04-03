package com.majorik.feature.search.domain.traktModels.sync

import com.squareup.moshi.Json

data class TraktItem(
    @field:Json(name = "title") val title: String?,
    @field:Json(name = "year") val year: Int?,
    @field:Json(name = "ids") val ids: Ids
) {
    data class Ids(
        @field:Json(name = "tarkt") val trakt: Int?,
        @field:Json(name = "tmdb") val tmdb: Int?,
        @field:Json(name = "imdb") val imdb: String?,
        @field:Json(name = "slug") val slug: String?,
        @field:Json(name = "tvdb") val tvdb: Int?
    )
}
