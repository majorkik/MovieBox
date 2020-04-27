package com.majorik.moviebox.feature.search.domain.tmdbModels.request

import com.squareup.moshi.Json

data class RequestMarkAsFavorite(
    @field:Json(name = "media_type") val mediaType: String,
    @field:Json(name = "media_id") val mediaId: Int,
    @field:Json(name = "favorite") val favorite: Boolean
)
