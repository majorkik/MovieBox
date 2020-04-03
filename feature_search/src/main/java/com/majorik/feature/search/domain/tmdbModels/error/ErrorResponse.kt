package com.majorik.feature.search.domain.tmdbModels.error

import com.squareup.moshi.Json

data class ErrorResponse(
    @field:Json(name = "status_message") val statusMessage: String?,
    @field:Json(name = "status_code") val statusCode: Int?
)
