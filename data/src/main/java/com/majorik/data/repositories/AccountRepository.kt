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
            errorMessage = "Ошибка при получении информации о пользователе"
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
        }, errorMessage = "Ошибка при получении понравившихся фильмов"
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
        }, errorMessage = "Ошибка при получении понравившихся сериалов"
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
            errorMessage = "Ошибка, неудалось установить флаг 'Нарвится'" +
                    " со значением: ${requestMarkAsFavorite.favorite} " +
                    "для типа: ${requestMarkAsFavorite.mediaType}" +
                    " c id: ${requestMarkAsFavorite.mediaId} "
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
            }, errorMessage = "Ошибка при получении оцененных фильмов"
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
            }, errorMessage = "Ошибка при получении оцененных сериалов"
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
            }, errorMessage = "Ошибка при получении оценненых серий"
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
        }, errorMessage = "Ошибка при получении списка фильмов 'Буду смотреть'"
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
        }, errorMessage = "Ошибка при получении списка сериалов 'Буду смотреть'"
    )
    }

    suspend fun addToWatchlist(requestAddToWatchlist: RequestAddToWatchlist, sessionId: String): ResponseApi? {
        return safeApiCall(
            call = {
                api.addToWatchlist(requestAddToWatchlist, sessionId).await()
            },
            errorMessage = "Неудалось добавть в 'Избранное'" +
                    " с значением: ${requestAddToWatchlist.watchlist} " +
                    "с типом: ${requestAddToWatchlist.mediaType} " +
                    "с id: ${requestAddToWatchlist.mediaId} "
        )
    }

}