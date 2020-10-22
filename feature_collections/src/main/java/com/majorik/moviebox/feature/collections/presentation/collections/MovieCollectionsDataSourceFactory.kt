package com.majorik.moviebox.feature.collections.presentation.collections

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.majorik.moviebox.feature.collections.data.repositories.MovieRepository
import com.majorik.moviebox.domain.enums.collections.MovieCollectionType
import com.majorik.moviebox.feature.collections.domain.tmdbModels.movie.Movie
import kotlinx.coroutines.CoroutineScope

class MovieCollectionsDataSourceFactory(
    private val repository: MovieRepository,
    private val scope: CoroutineScope,
    private val movieCollectionType: MovieCollectionType
) : DataSource.Factory<Int, Movie>() {
    val source = MutableLiveData<MovieCollectionsDataSource>()

    override fun create(): DataSource<Int, Movie> {
        val source =
            MovieCollectionsDataSource(
                repository,
                scope,
                movieCollectionType
            )
        this.source.postValue(source)
        return source
    }

    fun getSource() = source.value

    fun refresh() {
        getSource()?.refresh()
    }
}
