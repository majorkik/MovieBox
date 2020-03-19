package com.majorik.data.repositories

import com.majorik.data.api.TraktApiService
import com.majorik.domain.tmdbModels.result.ResultWrapper
import com.majorik.domain.traktModels.oauth.GrantType
import com.majorik.domain.traktModels.oauth.OAuthToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Response

class TraktAuthRepository(private val apiTraktService: TraktApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun exchangeCodeForAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String
    ): ResultWrapper<Response<OAuthToken>> = safeApiCall(dispatcher) {
        apiTraktService.exchangeCodeForAccessToken(
            code,
            clientId,
            clientSecret,
            redirectUri,
            GrantType.AUTHORIZATION_CODE.id
        )
    }

    suspend fun exchangeRefreshTokenForAccessToken(
        refreshToken: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String
    ) {
        safeApiCall(dispatcher) {
            apiTraktService.exchangeRefreshTokenForAccessToken(
                refreshToken,
                clientId,
                clientSecret,
                redirectUri,
                GrantType.REFRESH_TOKEN.id
            )
        }
    }
}
