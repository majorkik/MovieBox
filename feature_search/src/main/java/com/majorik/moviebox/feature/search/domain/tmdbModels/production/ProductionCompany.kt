package com.majorik.moviebox.feature.search.domain.tmdbModels.production

import com.squareup.moshi.Json

internal data class ProductionCompany(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "logo_path") val logoPath: String?,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "origin_country") val originCountry: String
)
