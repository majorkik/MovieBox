package com.majorik.moviebox.feature.details.domain.traktModels.oauth

enum class GrantType(val id: String) {
    REFRESH_TOKEN("refresh_token"),
    AUTHORIZATION_CODE("authorization_code")
}
