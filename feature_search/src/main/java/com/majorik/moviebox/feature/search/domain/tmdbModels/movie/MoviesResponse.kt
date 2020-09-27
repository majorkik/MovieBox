package com.majorik.moviebox.feature.search.domain.tmdbModels.movie

import com.squareup.moshi.Json

data class MoviesResponse(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "results") val results: List<Movie>,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int
)
