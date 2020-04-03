package com.majorik.feature.search.domain.tmdbModels.cast

import com.squareup.moshi.Json

data class Cast(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "credit_id") val creditId: String,
    @field:Json(name = "character") val character: String?,
    @field:Json(name = "order") val order: Int?,
    @field:Json(name = "profile_path") val profilePath: String?
)
