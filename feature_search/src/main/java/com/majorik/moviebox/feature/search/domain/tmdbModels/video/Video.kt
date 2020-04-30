package com.majorik.moviebox.feature.search.domain.tmdbModels.video

import com.squareup.moshi.Json

internal data class Video(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "iso_639_1") val iso_639_1: String,
    @field:Json(name = "iso_3166_1") val iso_3166_1: String,
    @field:Json(name = "key") val key: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "site") val site: String,
    @field:Json(name = "size") val size: Int,
    @field:Json(name = "type") val type: String
)
