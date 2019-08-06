package com.majorik.domain.tmdbModels

import com.squareup.moshi.Json

data class ResponseSession(
    @field:Json(name = "success") val success: Boolean?,
    @field:Json(name = "session_id") val sessionId: String?,
    @field:Json(name = "status_code") val statusCode: Int?,
    @field:Json(name = "status_message") val statusMessage: String?
)