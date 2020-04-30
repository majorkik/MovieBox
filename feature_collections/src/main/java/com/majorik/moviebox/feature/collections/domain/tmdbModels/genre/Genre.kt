package com.majorik.moviebox.feature.collections.domain.tmdbModels.genre

import com.squareup.moshi.Json

data class Genre(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    val imagePath: String? = null
)
