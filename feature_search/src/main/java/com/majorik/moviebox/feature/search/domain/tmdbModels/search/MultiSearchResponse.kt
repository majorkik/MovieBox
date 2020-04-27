package com.majorik.moviebox.feature.search.domain.tmdbModels.search

import com.squareup.moshi.Json

data class MultiSearchResponse(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "results") val results: List<MultiSearchItem>,
    @field:Json(name = "total_pages") val totalPages: Int,
    @field:Json(name = "total_results") val totalResults: Int
) {
    data class MultiSearchItem(
        @field:Json(name = "id") val id: Int,
        // posterPath or profilePath
        @field:Json(name = "poster_path") val posterPath: String?,
        @field:Json(name = "profile_path") val profilePath: String?,
        @field:Json(name = "media_type") val mediaType: String,
        // name or title
        @field:Json(name = "name") val name: String?,
        @field:Json(name = "title") val title: String?

    )
}
