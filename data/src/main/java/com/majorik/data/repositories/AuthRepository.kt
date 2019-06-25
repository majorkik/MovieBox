package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.models.RequestTokenResponse
import com.majorik.domain.models.ResponseSession
import com.majorik.domain.models.request.RequestSession
import com.majorik.domain.models.request.RequestToken

class AuthRepository(private val api: TmdbApiService) : BaseRepository() {
    suspend fun createSession(token: String): ResponseSession? {
        val requestToken = RequestToken(token)
        return safeApiCall(
            call = { api.createSession(requestToken).await() },
            errorMessage = "Ошибка POST[createSession]\n" +
                    "(token = $token)"
        )
    }

    suspend fun getRequestToken(): RequestTokenResponse? {
        return safeApiCall(
            call = {
                api.getRequestToken().await()
            },
            errorMessage = "Ошибка GET[getRequestToken]"
        )
    }

    suspend fun deleteSession(sessionIdModel: RequestSession): ResponseSession? {
        return safeApiCall(
            call = {
                api.deleteSession(sessionIdModel).await()
            },
            errorMessage = "Ошибка DELETE[deleteSession]\n" +
                    "(sessionId = ${sessionIdModel.sessionId})"
        )
    }


}