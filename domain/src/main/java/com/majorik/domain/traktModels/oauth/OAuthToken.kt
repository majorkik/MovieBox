package com.majorik.domain.traktModels.oauth

import com.squareup.moshi.Json

data class OAuthToken(
    @field:Json(name = "access_token") val accessToken: String,
    @field:Json(name = "refresh_token") val refreshToken: String,
    @field:Json(name = "scope") val scope: String,
    @field:Json(name = "token_type") val tokeType: String,
    @field:Json(name = "expires_in") val expiresIn: Long,
    @field:Json(name = "created_at") val createdAt: Long
)
