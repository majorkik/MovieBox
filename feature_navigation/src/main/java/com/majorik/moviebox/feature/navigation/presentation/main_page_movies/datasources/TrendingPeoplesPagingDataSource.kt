package com.majorik.moviebox.feature.navigation.presentation.main_page_movies.datasources

import androidx.paging.PagingSource
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.domain.enums.collections.MovieCollectionType.*
import com.majorik.moviebox.feature.navigation.data.repositories.PersonRepository
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.person.Person
import com.orhanobut.logger.Logger

class TrendingPeoplesPagingDataSource(
    private val repository: PersonRepository,
    private val language: String?
) : PagingSource<Int, Person>() {

    private var totalPages: Int? = null

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Person> {
        // Start refresh at page 1 if undefined.
        val nextPageNumber = params.key ?: 1

        if (totalPages != null && nextPageNumber > totalPages!!) {
            return LoadResult.Error(Exception("Max page. nextPage: $nextPageNumber, maxPage: $totalPages"))
        }

        return when (val response = repository.getPopularPeoples(language, nextPageNumber)) {
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
}
