package com.majorik.moviebox.feature.navigation.domain.tmdbModels.image

import com.squareup.moshi.Json

data class PersonPostersResponse(
    @field:Json(name = "profiles") val profiles: List<ImageDetails>
)
