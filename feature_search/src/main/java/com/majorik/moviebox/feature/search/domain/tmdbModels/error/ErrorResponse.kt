package com.majorik.moviebox.feature.search.domain.tmdbModels.error

import com.squareup.moshi.Json

internal data class ErrorResponse(
    @field:Json(name = "status_message") val statusMessage: String?,
    @field:Json(name = "status_code") val statusCode: Int?
)
