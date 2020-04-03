package com.majorik.feature.collections.data.repositories

import com.majorik.feature.collections.data.api.TmdbApiService
import com.majorik.feature.collections.domain.tmdbModels.account.AccountStates
import com.majorik.feature.collections.domain.tmdbModels.genre.GenreResponse
import com.majorik.feature.collections.domain.tmdbModels.tv.TVDetails
import com.majorik.feature.collections.domain.tmdbModels.tv.TVResponse
import com.majorik.feature.collections.domain.tmdbModels.tv.TVSeasonDetails
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TVRepository(private val api: TmdbApiService) : BaseRepository() {

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

    suspend fun getPopularTVs(
        language: String?,
        page: Int?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) { api.getPopularTVs(language, page) }
    }

    suspend fun getTopRatedTVs(
        language: String?,
        page: Int?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) { api.getTopRatedTVs(language, page) }
    }

    suspend fun getTVGenres(language: String?): ResultWrapper<GenreResponse> {
        return safeApiCall(dispatcher) { api.getTVGenres(language) }
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

    suspend fun getAiringTodayTVs(language: String?, page: Int?): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) {
            api.getAiringTodayTVs(language, page)
        }
    }

    suspend fun getOnTheAirTVs(language: String?, page: Int?): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) {
            api.getOnTheAirTVs(language, page)
        }
    }
}
