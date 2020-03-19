package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.auth.RequestTokenResponse
import com.majorik.domain.tmdbModels.auth.ResponseSession
import com.majorik.domain.tmdbModels.request.RequestSession
import com.majorik.domain.tmdbModels.request.RequestToken
import com.majorik.domain.tmdbModels.result.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TmdbAuthRepository(private val api: TmdbApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun createSession(token: String): ResultWrapper<ResponseSession> {
        val requestToken = RequestToken(token)
        return safeApiCall(dispatcher) { api.createSession(requestToken) }
    }

    suspend fun getRequestToken(): ResultWrapper<RequestTokenResponse> {
        return safeApiCall(dispatcher) {
            api.getRequestToken()
        }
    }

    suspend fun deleteSession(sessionIdModel: RequestSession): ResultWrapper<ResponseSession> {
        return safeApiCall(dispatcher) {
            api.deleteSession(sessionIdModel)
        }
    }
}
