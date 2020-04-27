package com.majorik.moviebox.feature.details.domain.tmdbModels.crew

import com.squareup.moshi.Json

data class Crew(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "credit_id") val creditId: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "department") val department: String,
    @field:Json(name = "job") val job: String,
    @field:Json(name = "profile_path") val profilePath: String?,
    @field:Json(name = "gender") val gender: Int
)
