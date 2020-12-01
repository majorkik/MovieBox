package com.majorik.moviebox.feature.search.data.repositories

import com.majorik.moviebox.feature.search.data.api.SearchApiService
import com.majorik.moviebox.feature.search.domain.tmdbModels.movie.MoviesResponse
import com.majorik.moviebox.feature.search.domain.tmdbModels.person.PersonResponse
import com.majorik.moviebox.feature.search.domain.tmdbModels.search.MultiSearchResponse
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TVsResponse
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class SearchRepository(private val api: SearchApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun multiSearch(
        language: String?,
        query: String,
        page: Int?,
        includeAdult: Boolean?
    ): ResultWrapper<MultiSearchResponse> {
        return safeApiCall(dispatcher) {
            api.multiSearch(language, query, page, includeAdult)
        }
    }

    suspend fun searchMovies(
        language: String?,
        query: String,
        page: Int?,
        includeAdult: Boolean?,
        region: String?,
        year: Int?,
        primaryReleaseYear: Int?
    ): ResultWrapper<MoviesResponse> {
        return safeApiCall(dispatcher) {
            api.searchMovies(
                language,
                query,
                page,
                includeAdult,
                region,
                year,
                primaryReleaseYear
            )
        }
    }

    suspend fun searchTVs(
        language: String?,
        query: String,
        page: Int?,
        firstAirDateYear: Int?
    ): ResultWrapper<TVsResponse> {
        return safeApiCall(dispatcher) {
            api.searchTVSeries(language, query, page, firstAirDateYear)
        }
    }

    suspend fun searchPeoples(
        language: String?,
        query: String,
        page: Int?,
        includeAdult: Boolean?,
        region: String?
    ): ResultWrapper<PersonResponse> {
        return safeApiCall(dispatcher) {
            api.searchPeoples(language, query, page, includeAdult, region)
        }
    }
}
