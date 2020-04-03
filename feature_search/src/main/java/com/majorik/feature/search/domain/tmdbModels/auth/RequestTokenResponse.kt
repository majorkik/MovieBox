package com.majorik.feature.search.domain.tmdbModels.auth

import com.squareup.moshi.Json

data class RequestTokenResponse(
    @field:Json(name = "success") val success: Boolean?,
    @field:Json(name = "expires_at") val expiresAt: String?,
    @field:Json(name = "request_token") val requestToken: String
)
