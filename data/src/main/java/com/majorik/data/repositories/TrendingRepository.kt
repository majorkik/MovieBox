package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.movie.Movie
import com.majorik.domain.tmdbModels.person.Person
import com.majorik.domain.tmdbModels.tv.TV

class TrendingRepository(private val api: TmdbApiService) : BaseRepository() {
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
    ): MutableList<Movie>? {
        val personResponse = safeApiCall(
            call = {
                api.getTrendingMovies(MediaType.MOVIE.path, timeWindow.path, page, language)
            },
            errorMessage = "Ошибка GET[getPersonTaggedImages]"
        )

        return personResponse?.results?.toMutableList()
    }

    suspend fun getTrendingTVs(timeWindow: TimeWindow, language: String?): MutableList<TV>? {
        val response = safeApiCall(
            call = {
                api.getTrendingTVs(MediaType.TV.path, timeWindow.path, language)
            },
            errorMessage = "ошибка GET[getTrendingTVs]"
        )

        return response?.results?.toMutableList()
    }

    suspend fun getTrendingPeoples(
        timeWindow: TimeWindow,
        language: String?
    ): MutableList<Person>? {
        val response = safeApiCall(
            call = {
                api.getTrendingPeoples(MediaType.PERSON.path, timeWindow.path, language)
            },
            errorMessage = "ошибка GET[getTrendingPeoples]"
        )

        return response?.results?.toMutableList()
    }
}
