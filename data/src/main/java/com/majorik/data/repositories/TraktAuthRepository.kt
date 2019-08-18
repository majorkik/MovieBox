package com.majorik.data.repositories

import com.majorik.data.api.TraktApiService
import com.majorik.domain.traktModels.oauth.GrantType
import com.majorik.domain.traktModels.oauth.OAuthToken

class TraktAuthRepository(private val apiTraktService: TraktApiService) : BaseRepository() {
    suspend fun exchangeCodeForAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String
    ): OAuthToken? = safeApiCall(
        call = {
            apiTraktService.exchangeCodeForAccessToken(
                code,
                clientId,
                clientSecret,
                redirectUri,
                GrantType.AUTHORIZATION_CODE.id
            ).await()
        },
        errorMessage = "Ошибка GET[exchangeCodeForAccessToken]\n" +
                "(code = $code, clientId = $clientId, clientSecret = $clientSecret)"
    )

    suspend fun exchangeRefreshTokenForAccessToken(
        refreshToken: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String
    ) {
        safeApiCall(
            call = {
                apiTraktService.exchangeRefreshTokenForAccessToken(
                    refreshToken,
                    clientId,
                    clientSecret,
                    redirectUri,
                    GrantType.REFRESH_TOKEN.id
                ).await()
            },
            errorMessage = "Ошибка GET[exchangeRefreshTokenForAccessToken]\n" +
                    "(code = $refreshToken, clientId = $clientId, clientSecret = $clientSecret)"
        )
    }

}