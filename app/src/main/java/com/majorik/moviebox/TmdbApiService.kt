package com.majorik.moviebox

import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface TmdbApiService {
    // Auth

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSession(
        @Body requestToken: RequestToken
    ): ResponseSession

    @HTTP(method = "DELETE", path = "authentication/session", hasBody = true)
    suspend fun deleteSession(
        @Body sessionIdModel: RequestSession
    ): ResponseSession


}
