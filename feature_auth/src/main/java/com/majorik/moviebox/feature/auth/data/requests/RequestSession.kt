package com.majorik.moviebox.feature.auth.data.requests

import com.squareup.moshi.Json

data class RequestSession(
    @field:Json(name = "session_id") val sessionId: String
)
