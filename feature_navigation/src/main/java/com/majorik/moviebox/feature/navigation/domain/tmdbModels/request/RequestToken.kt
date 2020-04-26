package com.majorik.moviebox.feature.navigation.domain.tmdbModels.request

import com.squareup.moshi.Json

data class RequestToken(
    @field:Json(name = "request_token") val requestToken: String
)
