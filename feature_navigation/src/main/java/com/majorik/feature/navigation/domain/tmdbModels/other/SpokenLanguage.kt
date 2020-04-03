package com.majorik.feature.navigation.domain.tmdbModels.other

import com.squareup.moshi.Json

data class SpokenLanguage(
    @field:Json(name = "iso_639_1") val iso6391: String,
    @field:Json(name = "name") val name: String
)
