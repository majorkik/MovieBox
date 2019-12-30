package com.majorik.domain.tmdbModels

import com.squareup.moshi.Json

data class ApiResponse(
    @field:Json(name = "status_code") val statusCode: Int,
    @field:Json(name = "status_message") val statusMessage: String
)