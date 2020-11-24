package com.majorik.moviebox.feature.collections.presentation.movieTabCollections.datasources

import androidx.paging.PagingSource
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.domain.enums.collections.MovieCollectionType
import com.majorik.moviebox.feature.collections.data.repositories.MovieRepository
import com.majorik.moviebox.feature.collections.domain.tmdbModels.movie.Movie
import com.majorik.moviebox.feature.collections.domain.tmdbModels.movie.MovieResponse
import com.orhanobut.logger.Logger

class MovieCollectionsPagingDataSource(
    private val repository: MovieRepository,
    private val collectionType: MovieCollectionType
) : PagingSource<Int, Movie>() {

    private var totalPages: Int? = null

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Movie> {
        // Start refresh at page 1 if undefined.
        val nextPageNumber = params.key ?: 1

        if (totalPages != null && nextPageNumber > totalPages!!) {
            return LoadResult.Error(Exception("Max page. nextPage: $nextPageNumber, maxPage: $totalPages"))
        }

        return when (val response = getMoviesCollectionsByType(nextPageNumber)) {
            is ResultWrapper.GenericError -> {
                Logger.e("Generic Error")

                LoadResult.Error(
                    Exception(
                        "Generic Error  ${response.error?.statusMessage} ${response.error?.statusCode}"
                    )
                )
            }

            is ResultWrapper.NetworkError -> {
                Logger.e("Network Error")
                LoadResult.Error(Exception("Network Error}"))
            }

            is ResultWrapper.Success -> {
                totalPages = response.value.totalPages

                LoadResult.Page(
                    data = response.value.results,
                    prevKey = null, // Only paging forward.
                    nextKey = nextPageNumber + 1
                )
            }
        }
    }

    private suspend fun getMoviesCollectionsByType(nextPage: Int): ResultWrapper<MovieResponse> {
        return when (collectionType) {
            MovieCollectionType.POPULAR -> {
                repository.getPopularMovies("ru", nextPage, null)
            }

            MovieCollectionType.TOP_RATED -> {
                repository.getTopRatedMovies("ru", nextPage, null)
            }

            MovieCollectionType.UPCOMING -> {
                repository.getUpcomingMovies("ru", nextPage, null)
            }

            MovieCollectionType.NOW_PLAYING -> {
                repository.getNowPlayingMovies("ru", nextPage, null)
            }
        }
    }
}
