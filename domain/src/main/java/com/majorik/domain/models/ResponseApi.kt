package com.majorik.domain.models

import com.squareup.moshi.Json

data class ResponseApi(
    @field:Json(name ="status_code") val statusCode: Int,
    @field:Json(name ="status_message") val statusMessage: String
)