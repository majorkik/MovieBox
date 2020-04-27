package com.majorik.moviebox.feature.details.domain.tmdbModels.person

import com.squareup.moshi.Json

data class Person(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "profile_path") val profilePath: String?,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "popularity") val popularity: Double,
    @field:Json(name = "known_for_department") val knowForDepartment: String?
)
