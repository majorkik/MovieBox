package com.majorik.domain.models

import com.squareup.moshi.Json

data class CollectionResponse(
    @field:Json(name = "page") val page: Int,
    @field:Json(name = "results") val results: List<CollectionItem>,
    @field:Json(name = "total_results") val totalResults: Int,
    @field:Json(name = "total_pages") val totalPages: Int
) {
    data class CollectionItem(
        @field:Json(name = "poster_path") val posterPath: String?,
        @field:Json(name = "id") val id: Int,
        //title или name
        @field:Json(name = "title") val title: String?,
        @field:Json(name = "name") val name: String?,
        //original_title или original_name
        @field:Json(name = "original_title") val originalTitle: String?,
        @field:Json(name = "original_name") val originalName: String?,
        @field:Json(name = "popularity") val popularity: Double,
        //release_date или first_air_date
        @field:Json(name = "release_date") val releaseDate: String?,
        @field:Json(name = "first_air_date") val firstAirDate: String?,
        @field:Json(name = "genre_ids") val genreIds: List<Int>,
        @field:Json(name = "vote_average") val voteAverage: Double
    )
}