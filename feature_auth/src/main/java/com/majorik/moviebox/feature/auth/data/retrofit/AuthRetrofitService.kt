package com.majorik.moviebox.feature.auth.data.retrofit

import com.majorik.moviebox.feature.auth.domain.RequestSession
import com.majorik.moviebox.feature.auth.domain.RequestToken
import com.majorik.moviebox.feature.auth.domain.RequestTokenResponse
import com.majorik.moviebox.feature.auth.domain.ResponseSession
import retrofit2.http.* // ktlint-disable no-wildcard-imports

internal interface AuthRetrofitService {

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
