package com.majorik.feature.navigation.data.repositories

import com.majorik.feature.navigation.data.api.TmdbApiService
import com.majorik.feature.navigation.domain.tmdbModels.account.AccountStates
import com.majorik.feature.navigation.domain.tmdbModels.genre.GenreResponse
import com.majorik.feature.navigation.domain.tmdbModels.tv.TVDetails
import com.majorik.feature.navigation.domain.tmdbModels.tv.TVResponse
import com.majorik.feature.navigation.domain.tmdbModels.tv.TVSeasonDetails
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TVRepository(private val api: TmdbApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getPopularTVs(
        language: String?,
        page: Int?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) { api.getPopularTVs(language, page) }
    }

    suspend fun getTVGenres(language: String?): ResultWrapper<GenreResponse> {
        return safeApiCall(dispatcher) { api.getTVGenres(language) }
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
