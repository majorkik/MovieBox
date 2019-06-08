package com.majorik.domain.models.tv

import com.squareup.moshi.Json

data class TVEpisodeResponse(
    @field:Json(name ="page") val page: Int,
    @field:Json(name ="results") val results: List<TVEpisodeDetails>,
    @field:Json(name ="total_results") val totalResults: Int,
    @field:Json(name ="total_pages") val totalPages: Int
)