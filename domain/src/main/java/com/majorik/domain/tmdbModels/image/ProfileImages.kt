package com.majorik.domain.tmdbModels.image

import com.majorik.domain.tmdbModels.image.ImageDetails
import com.squareup.moshi.Json

data class ProfileImages(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "profiles") val profiles: List<ImageDetails>
)