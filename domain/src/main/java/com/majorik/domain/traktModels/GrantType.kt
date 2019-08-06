package com.majorik.domain.traktModels

enum class GrantType(val id: String) {
    REFRESH_TOKEN("refresh_token"),
    AUTHORIZATION_CODE("authorization_code")
}