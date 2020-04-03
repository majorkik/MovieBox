package com.majorik.feature.collections.data.repositories

import com.majorik.feature.collections.data.api.TmdbApiService
import com.majorik.feature.collections.domain.tmdbModels.movie.MovieResponse
import com.majorik.feature.collections.domain.tmdbModels.person.PersonResponse
import com.majorik.feature.collections.domain.tmdbModels.tv.TVResponse
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class TrendingRepository(private val api: TmdbApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    enum class MediaType(val path: String) {
        ALL("all"), MOVIE("movie"), TV("tv"), PERSON("person")
    }

    enum class TimeWindow(val path: String) {
        DAY("day"), WEEK("week")
    }

    suspend fun getTrendingMovies(
        timeWindow: TimeWindow,
        page: Int?,
        language: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) {
            api.getTrendingMovies(MediaType.MOVIE.path, timeWindow.path, page, language)
        }
    }

    suspend fun getTrendingTVs(
        timeWindow: TimeWindow,
        page: Int?,
        language: String?
    ): ResultWrapper<TVResponse> {
        return safeApiCall(dispatcher) {
            api.getTrendingTVs(MediaType.TV.path, timeWindow.path, page, language)
        }
    }

    suspend fun getTrendingPeoples(
        timeWindow: TimeWindow,
        language: String?
    ): ResultWrapper<PersonResponse> {
        return safeApiCall(dispatcher) {
            api.getTrendingPeoples(MediaType.PERSON.path, timeWindow.path, language)
        }
    }
}
