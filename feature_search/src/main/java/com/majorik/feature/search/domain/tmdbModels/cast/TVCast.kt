package com.majorik.feature.search.domain.tmdbModels.cast

import com.squareup.moshi.Json

data class TVCast(
    @field:Json(name = "backdrop_path") val backdropPath: String?,
    @field:Json(name = "character") val character: String,
    @field:Json(name = "credit_id") val creditId: String,
    @field:Json(name = "episode_count") val episodeCount: Int,
    @field:Json(name = "first_air_date") val firstAirDate: String?,
    @field:Json(name = "genre_ids") val genreIds: List<Int>,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "origin_country") val originCountry: List<String>,
    @field:Json(name = "original_language") val originalLanguage: String,
    @field:Json(name = "original_name") val originalName: String,
    @field:Json(name = "overview") val overview: String,
    @field:Json(name = "popularity") val popularity: Double,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "vote_average") val voteAverage: Double,
    @field:Json(name = "vote_count") val voteCount: Int
)
