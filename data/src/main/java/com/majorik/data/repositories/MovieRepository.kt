package com.majorik.data.repositories

import com.majorik.data.api.TmdbApiService
import com.majorik.domain.models.account.AccountStates
import com.majorik.domain.models.genre.Genre
import com.majorik.domain.models.movie.MovieDetails
import com.majorik.domain.models.movie.MovieResponse

class MovieRepository(private val api: TmdbApiService) : BaseRepository() {
    suspend fun getMovieById(
        movieId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ): MovieDetails? {
        return safeApiCall(
            call = {
                api.getMovieById(movieId, language, appendToResponse, imageLanguages).await()
            },
            errorMessage = "Ошибка при получении информации о фильме"
        )
    }

    suspend fun getPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ): MutableList<MovieResponse.Movie>? {
        val movieResponse = safeApiCall(
            call = { api.getPopularMovies(language, page, region).await() },
            errorMessage = "Ошибка при получения популярных фильмов"
        )

        return movieResponse?.results?.toMutableList()
    }

    suspend fun getTopRatedMovies(
        language: String?,
        page: Int?,
        region: String?
    ): MutableList<MovieResponse.Movie>? {
        val movieResponse = safeApiCall(
            call = { api.getTopRatedMovies(language, page, region).await() },
            errorMessage = "Ошибка при получении самых популярных фильмов"
        )

        return movieResponse?.results?.toMutableList()
    }

    suspend fun getMovieGenres(language: String?): MutableList<Genre>? {
        val movieResponse = safeApiCall(
            call = { api.getMovieGenres(language).await() },
            errorMessage = "Ошибка при получении списка жанров для фильмов"
        )

        return movieResponse?.genres?.toMutableList()
    }

    suspend fun getAccountStatesForMovie(movieId: Int, sessionId: String, guestSessionId: String?): AccountStates? {
        return safeApiCall(
            call = {
                api.getAccountStatesForMovie(movieId, sessionId, guestSessionId).await()
            },
            errorMessage = "Ошибка при получении данных о фильме (id:  $movieId ) в аккаунте пользователя"
        )
    }
}