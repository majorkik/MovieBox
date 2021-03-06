package com.majorik.moviebox.feature.collections.data.repositories

import com.majorik.moviebox.feature.collections.data.api.CollectionApiService
import com.majorik.moviebox.feature.collections.domain.tmdbModels.genre.GenreResponse
import com.majorik.moviebox.feature.collections.domain.tmdbModels.movie.MovieResponse
import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class MovieRepository(private val api: CollectionApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun getPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) { api.getPopularMovies(language, page, region) }
    }

    suspend fun getTopRatedMovies(
        language: String?,
        page: Int?,
        region: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) { api.getTopRatedMovies(language, page, region) }
    }

    suspend fun getMovieGenres(language: String?): ResultWrapper<GenreResponse> {
        return safeApiCall(dispatcher) { api.getMovieGenres(language) }
    }

    suspend fun getUpcomingMovies(
        language: String?,
        page: Int?,
        region: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) {
            api.getUpcomingMovies(language, page, region)
        }
    }

    suspend fun getNowPlayingMovies(
        language: String?,
        page: Int?,
        region: String?
    ): ResultWrapper<MovieResponse> {
        return safeApiCall(dispatcher) {
            api.getNowPlayingMovies(language, page, region)
        }
    }
}
