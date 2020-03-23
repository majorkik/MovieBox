package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.movie.MovieResponse
import com.majorik.domain.tmdbModels.person.PersonResponse
import com.majorik.domain.tmdbModels.result.ResultWrapper
import com.majorik.domain.tmdbModels.tv.TV
import com.majorik.domain.tmdbModels.tv.TVResponse
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
