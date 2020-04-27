package com.majorik.moviebox.feature.details.domain.tmdbModels.account

import com.squareup.moshi.Json

data class AccountDetails(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "include_adult") val includeAdult: Boolean,
    @field:Json(name = "username") val username: String
)
