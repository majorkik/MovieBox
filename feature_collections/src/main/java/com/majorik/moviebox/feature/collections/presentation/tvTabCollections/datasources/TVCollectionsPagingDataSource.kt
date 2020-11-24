package com.majorik.moviebox.feature.collections.presentation.tvTabCollections.datasources

import androidx.paging.PagingSource
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.domain.enums.collections.TVCollectionType
import com.majorik.moviebox.feature.collections.data.repositories.TVRepository
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.collections.domain.tmdbModels.tv.TVResponse
import com.orhanobut.logger.Logger

class TVCollectionsPagingDataSource(
    private val repository: TVRepository,
    private val collectionType: TVCollectionType,
    private val language: String? = null
) : PagingSource<Int, TV>() {

    private var totalPages: Int? = null

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, TV> {
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

    private suspend fun getMoviesCollectionsByType(nextPage: Int): ResultWrapper<TVResponse> {
        return when (collectionType) {
            TVCollectionType.POPULAR -> {
                repository.getPopularTVs(language, nextPage)
            }
            TVCollectionType.TOP_RATED -> {
                repository.getTopRatedTVs(language, nextPage)
            }
            TVCollectionType.AIRING_TODAY -> {
                repository.getAiringTodayTVs(language, nextPage)
            }
            TVCollectionType.ON_THE_AIR -> {
                repository.getAiringTodayTVs(language, nextPage)
            }
        }
    }
}
