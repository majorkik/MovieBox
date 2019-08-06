package com.majorik.moviebox.ui.login

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.TraktAuthRepository
import com.majorik.domain.traktModels.OAuthToken
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class LoginPageViewModel(private val traktAuthRepository: TraktAuthRepository) : BaseViewModel() {
    val oauthAccessTokenLiveData = MutableLiveData<OAuthToken>()

    fun exchangeCodeForAccessToken(
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String
    ) {
        ioScope.launch {
            val accessToken = traktAuthRepository.exchangeCodeForAccessToken(
                code,
                clientId,
                clientSecret,
                redirectUri
            )
            oauthAccessTokenLiveData.postValue(accessToken)
        }
    }
}