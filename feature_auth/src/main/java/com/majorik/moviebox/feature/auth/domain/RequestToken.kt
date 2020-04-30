package com.majorik.moviebox.feature.auth.domain

import com.squareup.moshi.Json

data class RequestToken(
    @field:Json(name = "request_token") val requestToken: String
)
