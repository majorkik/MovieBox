package com.majorik.moviebox.feature.search.presentation.ui.search.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import com.majorik.moviebox.feature.search.presentation.ui.search.datasource.SearchMoviePagingDataSource
import kotlinx.coroutines.flow.MutableStateFlow

internal class SearchMovieViewModel(
    repository: SearchRepository
) : ViewModel() {

    val query = MutableStateFlow("")

    var searchMoviesDataSource: SearchMoviePagingDataSource? = null

    var searchMoviesFlow = Pager(PagingConfig(pageSize = 20)) {
        SearchMoviePagingDataSource(query.value, repository).also {
            searchMoviesDataSource = it
        }
    }.flow.cachedIn(viewModelScope)
}
