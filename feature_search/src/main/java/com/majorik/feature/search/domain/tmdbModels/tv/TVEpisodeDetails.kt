package com.majorik.feature.search.domain.tmdbModels.tv

import com.majorik.feature.search.domain.tmdbModels.cast.Cast
import com.majorik.feature.search.domain.tmdbModels.crew.Crew
import com.squareup.moshi.Json

data class TVEpisodeDetails(
    @field:Json(name = "air_date") val airData: String,
    @field:Json(name = "crew") val crew: List<Crew>,
    @field:Json(name = "episode_number") val episodeNumber: Int,
    @field:Json(name = "guest_stars") val cast: List<Cast>,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "overview") val overview: String,
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "production_code") val productionCode: String?,
    @field:Json(name = "season_number") val season_number: Int,
    @field:Json(name = "still_path") val still_path: String?,
    @field:Json(name = "vote_average") val vote_average: Double,
    @field:Json(name = "vote_count") val vote_count: Int
)
