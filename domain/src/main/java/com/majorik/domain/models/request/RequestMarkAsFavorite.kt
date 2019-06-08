package com.majorik.domain.models.request

import com.squareup.moshi.Json

data class RequestMarkAsFavorite (
    @field:Json(name = "media_type") val mediaType: String,
    @field:Json(name = "media_id") val mediaId: Int,
    @field:Json(name = "favorite") val favorite: Boolean
)