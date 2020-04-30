package com.majorik.moviebox.feature.search.domain.tmdbModels.production

import com.squareup.moshi.Json

internal data class ProductionCountry(
    @field:Json(name = "iso_3166_1") val iso31661: String,
    @field:Json(name = "name") val name: String
)
