package com.majorik.moviebox.feature.navigation.presentation.main_page_profile.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.navigation.data.repositories.AccountRepository
import com.majorik.moviebox.feature.navigation.domain.enums.ProfileMoviesType.*
import com.majorik.moviebox.feature.navigation.domain.enums.ProfileTVsType
import com.majorik.moviebox.feature.navigation.domain.enums.ProfileTVsType.*
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TV
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.tv.TVResponse
import com.orhanobut.logger.Logger

class ProfileTVsPagingDataSource(
    private val sessionId: String,
    private val repository: AccountRepository,
    private val type: ProfileTVsType,
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

        return when (val response = getProfileTVsByType(nextPageNumber)) {
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

    private suspend fun getProfileTVsByType(nextPage: Int): ResultWrapper<TVResponse> {
        return when (type) {
            FAVORITE_TVS -> {
                repository.getFavoriteTVs(language, sessionId, "created_at.asc", nextPage)
            }

            WATCHLIST_TVS -> {
                repository.getWatchlistTVs(language, sessionId, "created_at.asc", nextPage)
            }

            RATED_TVS -> {
//                repository.getRate(language, sessionId, "created_at.asc", nextPage)
                TODO("Добавить запрос для оцененных фильмов")
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TV>): Int? = null
}
