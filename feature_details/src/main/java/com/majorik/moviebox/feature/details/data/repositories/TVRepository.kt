package com.majorik.moviebox.feature.details.data.repositories

import com.majorik.moviebox.feature.details.data.api.DetailsRetrofitService
import com.majorik.moviebox.feature.details.domain.tmdbModels.account.AccountStates
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.TVDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.tv.TVSeasonDetails
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

 class TVRepository(private val api: DetailsRetrofitService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getTVById(
        tvId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ): ResultWrapper<TVDetails> {
        return safeApiCall(dispatcher) {
            api.getTVById(
                tvId,
                language,
                appendToResponse,
                imageLanguages
            )
        }
    }

    suspend fun getTVSeasonDetails(
        tvId: Int,
        seasonNumber: Int,
        language: String?,
        appendToResponse: String?
    ): ResultWrapper<TVSeasonDetails> {
        return safeApiCall(dispatcher) {
            api.getSeasonDetails(
                tvId,
                seasonNumber,
                language,
                appendToResponse
            )
        }
    }

    suspend fun getAccountStatesForTV(
        tvId: Int,
        language: String?,
        sessionId: String,
        guestSessionId: String?
    ): ResultWrapper<AccountStates> {
        return safeApiCall(dispatcher) {
            api.getAccountStatesForTV(tvId, language, guestSessionId, sessionId)
        }
    }
}
