package com.majorik.moviebox.feature.search.presentation.pagination.movies

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.majorik.moviebox.feature.search.data.repositories.SearchRepository
import com.majorik.moviebox.feature.search.domain.tmdbModels.movie.Movie
import kotlinx.coroutines.CoroutineScope

internal class SearchMovieDataSourceFactory(
    private val repository: SearchRepository,
    private var query: String = "",
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Movie>() {
    val source = MutableLiveData<SearchMovieDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source =
            SearchMovieDataSource(
                repository,
                query,
                scope
            )
        this.source.postValue(source)
        return source
    }

    fun getQuery() = query

    fun getSource() = source.value

    fun updateQuery(query: String) {
        this.query = query
        getSource()?.refresh()
    }
}
