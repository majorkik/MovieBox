package com.majorik.moviebox.ui.genres

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.data.repositories.MovieRepository
import com.majorik.data.repositories.TVRepository
import com.majorik.domain.constants.AppConfig
import com.majorik.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.ui.genres.GenresActivity.GenresType
import kotlinx.coroutines.launch

class GenresViewModel(
    private val movieRepository: MovieRepository,
    private val tvRepository: TVRepository
) : ViewModel() {
    var genresLiveData = MutableLiveData<MutableList<Genre>>()

    fun fetchGenres(genresType: Int) {
        viewModelScope.launch {
            val response = when (genresType) {
                GenresType.TV_GENRES.ordinal -> {
                    tvRepository.getTVGenres(AppConfig.REGION)
                }

                else -> {
                    movieRepository.getMovieGenres(AppConfig.REGION)
                }
            }

            response?.let { genresLiveData.postValue(it) }
        }
    }
}