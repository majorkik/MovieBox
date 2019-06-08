package com.majorik.domain.models.image

import com.squareup.moshi.Json

data class PersonPostersResponse(
    @field:Json(name = "profiles") val profiles: List<ImageDetails>
)