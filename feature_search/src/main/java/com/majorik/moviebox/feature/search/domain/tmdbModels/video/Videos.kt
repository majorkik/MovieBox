package com.majorik.moviebox.feature.search.domain.tmdbModels.video

import com.squareup.moshi.Json

data class Videos(
    @field:Json(name = "results") val results: List<Video>
)
