package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.models.ResponseApi
import com.majorik.domain.models.account.AccountDetails
import com.majorik.domain.models.movie.MovieResponse
import com.majorik.domain.models.request.RequestAddToWatchlist
import com.majorik.domain.models.request.RequestMarkAsFavorite
import com.majorik.domain.models.tv.TVEpisodeResponse
import com.majorik.domain.models.tv.TVResponse

class AccountRepository(private val api: TmdbApiService) : BaseRepository() {
    suspend fun getAccountDetails(sessionId: String): AccountDetails? {
        return safeApiCall(
            call = {
                api.getAccountDetails(sessionId).await()
            },
            errorMessage = "Ошибка GET[getAccountDetails]\n" +
                    "(sessionId = $sessionId)"
        )
    }

    suspend fun getFavoriteMovies(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): MovieResponse? {

        return safeApiCall(
            call = {
                api.getFavoriteMovies(language, sessionId, sortBy, page).await()
            }, errorMessage = "Ошибка GET[getFavoriteMovies]\n" +
                    "(sessionId = $sessionId)"
        )
    }

    suspend fun getFavoriteTVs(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): TVResponse? {

        return safeApiCall(
            call = {
                api.getFavoriteTVs(language, sessionId, sortBy, page).await()
            }, errorMessage = "Ошибка GET[getFavoriteTVs]\n" +
                    "(sessionId = $sessionId)"
        )
    }

    suspend fun markIsFavorite(
        requestMarkAsFavorite: RequestMarkAsFavorite,
        sessionId: String
    ): ResponseApi? {
        return safeApiCall(
            call = {
                api.markIsFavorite(requestMarkAsFavorite, sessionId).await()
            },
            errorMessage = "Ошибка POST[markIsFavorite]\n" +
                    "(mediaType = ${requestMarkAsFavorite.mediaType}" +
                    "mediaId = ${requestMarkAsFavorite.mediaId}, sessionId = $sessionId) "
        )
    }

    suspend fun getRatedMovies(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): MovieResponse? {
        return safeApiCall(
            call = {
                api.getRatedMovies(language, sessionId, sortBy, page).await()
            }, errorMessage = "Ошибка GET[getRatedMovies]\n" +
                    "(sessionId = $sessionId)"
        )
    }

    suspend fun getRatedTVs(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): TVResponse? {
        return safeApiCall(
            call = {
                api.getRatedTVs(language, sessionId, sortBy, page).await()
            }, errorMessage = "Ошибка GET[getRatedTVs]\n" +
                    "(sessionId = $sessionId)"
        )
    }

    suspend fun getRatedEpisodes(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): TVEpisodeResponse? {
        return safeApiCall(
            call = {
                api.getRatedEpisodes(language, sessionId, sortBy, page).await()
            }, errorMessage = "Ошибка GET[getRatedEpisodes]\n" +
                    "(sessionId = $sessionId)"
        )
    }

    suspend fun getWatchlistMovies(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): MovieResponse? {
        return safeApiCall(
            call = {
                api.getWatchlistMovies(language, sessionId, sortBy, page).await()
            }, errorMessage = "Ошибка GET[getWatchlistMovies]\n" +
                    "(sessionId = $sessionId)"
        )
    }

    suspend fun getWatchlistTVs(
        language: String?,
        sessionId: String,
        sortBy: String?,
        page: Int?
    ): TVResponse? {

        return safeApiCall(
            call = {
                api.getWatchlistTVs(language, sessionId, sortBy, page).await()
            }, errorMessage = "Ошибка GET[getWatchlistTVs]\n" +
                    "(sessionId = $sessionId)"
        )
    }

    suspend fun addToWatchlist(
        requestAddToWatchlist: RequestAddToWatchlist,
        sessionId: String
    ): ResponseApi? {
        return safeApiCall(
            call = {
                api.addToWatchlist(requestAddToWatchlist, sessionId).await()
            },
            errorMessage = "Ошибка POST[addToWatchlist]\n" +
                    "(mediaType = ${requestAddToWatchlist.mediaType}, " +
                    "mediaId = ${requestAddToWatchlist.mediaId}], sessionId = $sessionId)"
        )
    }

}