package com.majorik.moviebox.feature.auth.data.repository

import com.majorik.moviebox.feature.auth.data.retrofit.AuthRetrofitService
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.*
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal class AuthTmdbRepository(private val api: AuthRetrofitService) : BaseRepository() {

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
