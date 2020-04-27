package com.majorik.moviebox.feature.search.domain.tmdbModels.request

import com.squareup.moshi.Json

data class RequestSession(
    @field:Json(name = "session_id") val sessionId: String
)
