package com.majorik.domain.tmdbModels.account

import com.squareup.moshi.Json

data class AccountStates(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "favorite") val favorite: Boolean,
    @field:Json(name = "watchlist") val watchlist: Boolean,
    @field:Json(name = "rated") val rated: Any?
) {
    fun getRated(): String{
        return when (rated) {
            is Double -> {
                rated.toDouble().toString()
            }
            else -> "-"
        }
    }
}