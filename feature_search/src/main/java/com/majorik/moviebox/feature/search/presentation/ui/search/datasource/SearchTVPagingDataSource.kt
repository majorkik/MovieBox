package com.majorik.moviebox.feature.search.presentation.ui.search.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TV
import com.orhanobut.logger.Logger

class SearchTVPagingDataSource(
    private val query: String,
    private val repository: SearchRepository
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

        return when (
            val response =
                repository.searchTVs(AppConfig.REGION, query, nextPageNumber, null)
        ) {
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

    override fun getRefreshKey(state: PagingState<Int, TV>): Int? = null
}
