package com.majorik.moviebox

import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
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
