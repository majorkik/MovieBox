package com.majorik.moviebox.feature.search.data.repositories

import com.majorik.library.base.base.BaseRepository
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.search.data.api.SearchApiService
import com.majorik.moviebox.feature.search.domain.models.discover.DiscoverFiltersModel
import com.majorik.moviebox.feature.search.domain.models.discover.getFiltersMapForMovies
import com.majorik.moviebox.feature.search.domain.models.discover.getFiltersMapForTVs
import com.majorik.moviebox.feature.search.domain.tmdbModels.movie.MoviesResponse
import com.majorik.moviebox.feature.search.domain.tmdbModels.tv.TVsResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DiscoverRepository(private val api: SearchApiService) : BaseRepository() {

    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun discoverMovies(
        language: String?,
        page: Int?,
        filtersModel: DiscoverFiltersModel
    ): ResultWrapper<MoviesResponse> {
        return safeApiCall(dispatcher) {
            api.discoverMovies(language, page, filtersModel.getFiltersMapForMovies())
        }
    }

    suspend fun discoverTVs(
        language: String?,
        page: Int?,
        filtersModel: DiscoverFiltersModel
    ): ResultWrapper<TVsResponse> {
        return safeApiCall(dispatcher) {
            api.discoverTVs(language, page, filtersModel.getFiltersMapForTVs())
        }
    }
}
