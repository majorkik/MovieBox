package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.search.MultiSearchResponse
import com.majorik.domain.tmdbModels.movie.MovieResponse
import com.majorik.domain.tmdbModels.person.PersonResponse
import com.majorik.domain.tmdbModels.tv.TVResponse

class SearchRepository(private val api: TmdbApiService) : BaseRepository() {

    suspend fun multiSearch(
        language: String?,
        query: String,
        page: Int?,
        includeAdult: Boolean?
    ): MultiSearchResponse? {
        return safeApiCall(
            call = {
                api.multiSearch(language, query, page, includeAdult)
            },
            errorMessage = "Ошибка GET[multiSearch]"
        )
    }

    suspend fun searchMovies(
        language: String?,
        query: String,
        page: Int?,
        includeAdult: Boolean?,
        region: String?,
        year: Int?,
        primaryReleaseYear: Int?
    ): MovieResponse? {
        return safeApiCall(
            call = {
                api.searchMovies(
                    language,
                    query,
                    page,
                    includeAdult,
                    region,
                    year,
                    primaryReleaseYear
                )
            },
            errorMessage = "Ошибка GET[searchMovies]"
        )
    }

    suspend fun searchTVs(
        language: String?,
        query: String,
        page: Int?,
        firstAirDateYear: Int?
    ): TVResponse? {
        return safeApiCall(
            call = {
                api.searchTVSeries(language, query, page, firstAirDateYear)
            },
            errorMessage = "Ошибка GET[searchTVs]"
        )
    }

    suspend fun searchPeoples(
        language: String?,
        query: String,
        page: Int?,
        includeAdult: Boolean?,
        region: String?
    ): PersonResponse? {
        return safeApiCall(
            call = {
                api.searchPeoples(language, query, page, includeAdult, region)
            },
            errorMessage = "Ошибка GET[searchPeoples]"
        )
    }
}