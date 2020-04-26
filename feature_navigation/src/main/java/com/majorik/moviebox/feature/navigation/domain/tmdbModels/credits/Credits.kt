package com.majorik.moviebox.feature.navigation.domain.tmdbModels.credits

import com.majorik.moviebox.feature.navigation.domain.tmdbModels.cast.Cast
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.crew.Crew
import com.squareup.moshi.Json

data class Credits(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "cast") val casts: List<Cast>,
    @field:Json(name = "crew") val crews: List<Crew>
)
