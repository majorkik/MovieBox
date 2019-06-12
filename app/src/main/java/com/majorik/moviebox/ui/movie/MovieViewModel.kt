package com.majorik.moviebox.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.majorik.data.repositories.MovieRepository
import com.majorik.domain.models.CollectionResponse
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {
    val popularMoviesLiveData = MutableLiveData<MutableList<CollectionResponse.CollectionItem>>()
    val topRatedMoviesLiveData = MutableLiveData<MutableList<CollectionResponse.CollectionItem>>()

    fun fetchPopularMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val popularMovies = movieRepository.getPopularMovies(language, page, region)
            popularMoviesLiveData.postValue(popularMovies)
        }
    }

    fun fetchTopRatedMovies(
        language: String?,
        page: Int?,
        region: String?
    ) {
        ioScope.launch {
            val topRatedMovies = movieRepository.getTopRatedMovies(language, page, region)
            topRatedMoviesLiveData.postValue(topRatedMovies)
        }
    }
}