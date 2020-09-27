package com.majorik.moviebox.feature.search.presentation.datasources

import androidx.paging.PagingSource
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.search.data.repositories.DiscoverRepository
import com.majorik.moviebox.feature.search.domain.enums.DiscoverType
import com.majorik.moviebox.feature.search.domain.models.discover.BaseDiscoverDomainModel
import com.majorik.moviebox.feature.search.domain.models.discover.DiscoverFiltersModel
import com.majorik.moviebox.feature.search.domain.models.discover.toDiscoverDomainModel
import com.orhanobut.logger.Logger

class DiscoverPagingDataSource(
    private val repository: DiscoverRepository,
    private val language: String?,
    private val filtersModel: DiscoverFiltersModel
) : PagingSource<Int, BaseDiscoverDomainModel>() {

    private var totalPages: Int? = null

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, BaseDiscoverDomainModel> {
        // Start refresh at page 1 if undefined.
        val nextPageNumber = params.key ?: 1

        if (totalPages != null && nextPageNumber > totalPages!!) {
            return LoadResult.Error(Exception("Max page. nextPage: $nextPageNumber, maxPage: $totalPages"))
        }

        return if (filtersModel.discoverType == DiscoverType.MOVIES) {
            loadMovies(nextPageNumber)
        } else {
            loadTVs(nextPageNumber)
        }
    }

    private suspend fun loadMovies(
        nextPageNumber: Int
    ): LoadResult<Int, BaseDiscoverDomainModel> {
        when (val response = repository.discoverMovies(language, nextPageNumber, filtersModel)) {
            is ResultWrapper.GenericError -> {
                Logger.e("Generic Error")

                return LoadResult.Error(
                    Exception(
                        "Generic Error  ${response.error?.statusMessage} ${response.error?.statusCode}"
                    )
                )
            }

            is ResultWrapper.NetworkError -> {
                Logger.e("Network Error")
                return LoadResult.Error(Exception("Network Error}"))
            }

            is ResultWrapper.Success -> {
                totalPages = response.value.totalPages

                return LoadResult.Page(
                    data = response.value.results.map { it.toDiscoverDomainModel() },
                    prevKey = null, // Only paging forward.
                    nextKey = nextPageNumber + 1
                )
            }
        }
    }

    private suspend fun loadTVs(
        nextPageNumber: Int
    ): LoadResult<Int, BaseDiscoverDomainModel> {
        when (val response = repository.discoverTVs(language, nextPageNumber, filtersModel)) {
            is ResultWrapper.GenericError -> {
                Logger.e("Generic Error")

                return LoadResult.Error(
                    Exception(
                        "Generic Error  ${response.error?.statusMessage} ${response.error?.statusCode}"
                    )
                )
            }

            is ResultWrapper.NetworkError -> {
                Logger.e("Network Error")
                return LoadResult.Error(Exception("Network Error}"))
            }

            is ResultWrapper.Success -> {
                totalPages = response.value.totalPages

                return LoadResult.Page(
                    data = response.value.results.map { it.toDiscoverDomainModel() },
                    prevKey = null, // Only paging forward.
                    nextKey = nextPageNumber + 1
                )
            }
        }
    }
}
