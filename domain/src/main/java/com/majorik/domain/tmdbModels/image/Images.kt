package com.majorik.domain.tmdbModels.image

import com.squareup.moshi.Json

data class Images(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "backdrops") val backdrops: List<ImageDetails>,
    @field:Json(name = "posters") val posters: List<ImageDetails>
)