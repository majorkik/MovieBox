package com.majorik.feature.search.domain.tmdbModels.image

import com.squareup.moshi.Json

data class ProfileImages(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "profiles") val profiles: List<ImageDetails>
)
