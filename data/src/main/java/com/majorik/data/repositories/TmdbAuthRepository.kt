package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.auth.RequestTokenResponse
import com.majorik.domain.tmdbModels.auth.ResponseSession
import com.majorik.domain.tmdbModels.request.RequestSession
import com.majorik.domain.tmdbModels.request.RequestToken

class TmdbAuthRepository(private val api: TmdbApiService) : BaseRepository() {
    suspend fun createSession(token: String): ResponseSession? {
        val requestToken = RequestToken(token)
        return safeApiCall(
            call = { api.createSession(requestToken) },
            errorMessage = "Ошибка POST[createSession]\n" +
                    "(token = $token)"
        )
    }

    suspend fun getRequestToken(): RequestTokenResponse? {
        return safeApiCall(
            call = {
                api.getRequestToken()
            },
            errorMessage = "Ошибка GET[getRequestToken]"
        )
    }

    suspend fun deleteSession(sessionIdModel: RequestSession): ResponseSession? {
        return safeApiCall(
            call = {
                api.deleteSession(sessionIdModel)
            },
            errorMessage = "Ошибка DELETE[deleteSession]\n" +
                    "(sessionId = ${sessionIdModel.sessionId})"
        )
    }


}