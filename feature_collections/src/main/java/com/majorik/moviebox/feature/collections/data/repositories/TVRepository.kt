package com.majorik.moviebox.feature.collections.data.repositories

import com.majorik.moviebox.feature.collections.data.api.CollectionApiService
import com.majorik.moviebox.feature.collections.domain.tmdbModels.genre.GenreResponse
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TVResponse
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TVRepository(private val api: CollectionApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

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
