package com.majorik.moviebox.feature.auth.data.retrofit

import com.majorik.moviebox.feature.auth.data.requests.RequestToken
import com.majorik.moviebox.feature.auth.data.responses.RequestTokenResponse
import com.majorik.moviebox.feature.auth.data.responses.ResponseSession
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

internal interface AuthRetrofitService {

    @GET("authentication/token/new")
    suspend fun getRequestToken(): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSession(@Body requestToken: RequestToken): ResponseSession
}
