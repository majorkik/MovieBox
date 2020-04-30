package com.majorik.moviebox.feature.auth.domain

import com.squareup.moshi.Json

data class RequestSession(
    @field:Json(name = "session_id") val sessionId: String
)
