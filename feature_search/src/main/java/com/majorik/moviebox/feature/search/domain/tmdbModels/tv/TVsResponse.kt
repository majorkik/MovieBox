package com.majorik.moviebox.feature.search.domain.tmdbModels.tv

import com.squareup.moshi.Json

data class TVsResponse(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "results") val results: List<TV>,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int
)
