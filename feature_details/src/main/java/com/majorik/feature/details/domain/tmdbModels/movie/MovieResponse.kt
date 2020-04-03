package com.majorik.feature.details.domain.tmdbModels.movie

import com.squareup.moshi.Json

data class MovieResponse(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "results") val results: List<Movie>,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int
)
