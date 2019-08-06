package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.tmdbModels.account.AccountStates
import com.majorik.domain.tmdbModels.genre.Genre
import com.majorik.domain.tmdbModels.tv.TVDetails
import com.majorik.domain.tmdbModels.tv.TVResponse
import com.majorik.domain.tmdbModels.tv.TVSeasonDetails


class TVRepository(private val api: TmdbApiService) : BaseRepository() {

    suspend fun getTVById(
        tvId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ): TVDetails? {

        return safeApiCall(
            call = { api.getTVById(tvId, language, appendToResponse, imageLanguages).await() },
            errorMessage = "Ошибка GET[getTVById]\n" +
                    "(tvId = $tvId)"
        )
    }

    suspend fun getPopularTVs(
        language: String?,
        page: Int?
    ): MutableList<TVResponse.TV>? {
        val tvResponse = safeApiCall(
            call = { api.getPopularTVs(language, page).await() },
            errorMessage = "Ошибка GET[getPopularTVs]"
        )

        return tvResponse?.results?.toMutableList()
    }

    suspend fun getTopRatedTVs(
        language: String?,
        page: Int?
    ): MutableList<TVResponse.TV>? {
        val tvResponse = safeApiCall(
            call = { api.getTopRatedTVs(language, page).await() },
            errorMessage = "Ошибка GET[getTopRatedTVs]"
        )

        return tvResponse?.results?.toMutableList()
    }

    suspend fun getTVGenres(language: String?): MutableList<Genre>? {
        val tvResponse = safeApiCall(
            call = { api.getTVGenres(language).await() },
            errorMessage = "Ошбика GET[getTVGenres]"
        )

        return tvResponse?.genres?.toMutableList()
    }

    suspend fun getTVSeasonDetails(
        tvId: Int,
        seasonNumber: Int,
        language: String?,
        appendToResponse: String?
    ): TVSeasonDetails? {
        return safeApiCall(
            call = { api.getSeasonDetails(tvId, seasonNumber, language, appendToResponse).await() },
            errorMessage = "Ошибка GET[getTVSeasonDetails]\n" +
                    "(tvId = $tvId, seasonNumber = $seasonNumber)"
        )
    }

    suspend fun getAccountStatesForTV(
        tvId: Int,
        language: String?,
        guestSessionId: String?,
        sessionId: String
    ): AccountStates? {
        return safeApiCall(
            call = {
                api.getAccountStatesForTV(tvId, language, guestSessionId, sessionId).await()
            },
            errorMessage = "Ошибка GET[getAccountStatesForTV]\n" +
                    " (tvId = $tvId, guestSessionId = $guestSessionId, sessionId = $sessionId)"
        )
    }

    suspend fun getAiringTodayTVs(language: String?, page: Int?): MutableList<TVResponse.TV>? {
        val tvResponse = safeApiCall(
            call = {
                api.getAiringTodayTVs(language, page).await()
            },
            errorMessage = "Ошибка GET[getAiringTodayTVs]"
        )

        return tvResponse?.results?.toMutableList()
    }

    suspend fun getOnTheAirTVs(language: String?, page: Int?): MutableList<TVResponse.TV>? {
        val tvResponse = safeApiCall(
            call = {
                api.getOnTheAirTVs(language, page).await()
            },
            errorMessage = "Ошибка GET[getOnTheAirTVs]"
        )

        return tvResponse?.results?.toMutableList()
    }
}  