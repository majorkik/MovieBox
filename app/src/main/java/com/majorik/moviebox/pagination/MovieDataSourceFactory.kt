package com.majorik.moviebox.pagination

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.majorik.data.repositories.MovieRepository
import com.majorik.domain.tmdbModels.movie.MovieCollectionType
import com.majorik.domain.tmdbModels.movie.MovieResponse
import kotlinx.coroutines.CoroutineScope

class MovieDataSourceFactory(
    private val repository: MovieRepository,
    private val scope: CoroutineScope,
    private val movieCollectionType: MovieCollectionType
) : DataSource.Factory<Int, MovieResponse.Movie>() {
    val source = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, MovieResponse.Movie> {
        val source = MovieDataSource(repository, scope, movieCollectionType)
        this.source.postValue(source)
        return source
    }

    fun getSource() = source.value

    fun refresh() {
        getSource()?.refresh()
    }
}