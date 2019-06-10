package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.models.CollectionResponse
import com.majorik.domain.models.account.AccountStates
import com.majorik.domain.models.genre.Genre
import com.majorik.domain.models.tv.TVDetails
import com.majorik.domain.models.tv.TVResponse


class TVRepository(private val api: TmdbApiService) : BaseRepository() {

    suspend fun getTVById(
        tvId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ): TVDetails? {

        return safeApiCall(
            call = { api.getTVById(tvId, language, appendToResponse, imageLanguages).await() },
            errorMessage = "Ошибка при получении информации о сериале"
        )
    }

    suspend fun getPopularTVs(
        language: String?,
        page: Int?
    ): MutableList<CollectionResponse.CollectionItem>? {
        val tvResponse = safeApiCall(
            call = { api.getPopularTVs(language, page).await() },
            errorMessage = "Ошибка при получении популярных сериалов"
        )

        return tvResponse?.results?.toMutableList()
    }

    suspend fun getTopRatedTVs(
        language: String?,
        page: Int?
    ): MutableList<CollectionResponse.CollectionItem>? {
        val tvResponse = safeApiCall(
            call = { api.getTopRatedTVs(language, page).await() },
            errorMessage = "Ошибка при получении самых популярных сериалов"
        )

        return tvResponse?.results?.toMutableList()
    }

    suspend fun getTVGenres(language: String?): MutableList<Genre>? {
        val tvResponse = safeApiCall(
            call = { api.getTVGenres(language).await() },
            errorMessage = "Ошбика при получении списка жанров для сериалов"
        )

        return tvResponse?.genres?.toMutableList()
    }

    suspend fun getAccountStatesForTV(tvId: Int, language: String?, guestSessionId: String?, sessionId: String): AccountStates? {
        return safeApiCall(
            call = {
                api.getAccountStatesForTV(tvId,language, guestSessionId, sessionId).await()
            },
            errorMessage = "Ошибка при получении данных о сериале (id:  $tvId ) в аккаунте пользователя"
        )
    }
}