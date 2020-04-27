package com.majorik.moviebox.feature.search.domain.tmdbModels.tv

import com.squareup.moshi.Json

data class TVSeasonDetails(
    @field:Json(name = "_id") val primaryId: String,
    @field:Json(name = "air_date") val airDate: String,
    @field:Json(name = "episodes") val episodes: List<Episode>,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "overview") val overview: String,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "poster_path") val posterPath: String?,
    @field:Json(name = "season_number") val seasonNumber: Int
)
