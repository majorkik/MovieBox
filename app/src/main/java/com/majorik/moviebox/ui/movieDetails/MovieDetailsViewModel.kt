package com.majorik.moviebox.ui.movieDetails

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.MovieRepository
import com.majorik.domain.models.movie.MovieDetails
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class MovieDetailsViewModel(private val movieRepository: MovieRepository) : BaseViewModel() {
    var movieDetailsLiveData = MutableLiveData<MovieDetails>()

    fun fetchMovieDetails(
        movieId: Int,
        language: String?,
        appendToResponse: String?,
        imageLanguages: String?
    ) {
        ioScope.launch {
            val movieDetails =
                movieRepository.getMovieById(movieId, language, appendToResponse, imageLanguages)

            movieDetailsLiveData.postValue(movieDetails)
        }
    }

}