package com.majorik.moviebox.feature.details.domain.tmdbModels.person

import com.squareup.moshi.Json

data class PersonResponse(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "results") val results: List<Person>
)
