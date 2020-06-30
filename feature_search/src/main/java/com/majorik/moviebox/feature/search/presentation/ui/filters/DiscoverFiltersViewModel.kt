package com.majorik.moviebox.feature.search.presentation.ui.filters

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.models.results.ResultWrapper
import com.majorik.moviebox.feature.navigation.data.repositories.MovieRepository
import com.majorik.moviebox.feature.navigation.data.repositories.TVRepository
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.Genre
import com.majorik.moviebox.feature.navigation.domain.tmdbModels.genre.GenreResponse
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

class DiscoverFiltersViewModel(
    private val movieRepository: MovieRepository,
    private val tvRepository: TVRepository
) : ViewModel() {

    val movieGenresLiveData = MutableLiveData<GenreResponse>()
    val tvGenresLiveData = MutableLiveData<GenreResponse>()

    fun getMovieGenres() {
        viewModelScope.launch {
            when (val response = movieRepository.getMovieGenres(AppConfig.REGION)) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    Logger.i(response.value.toString())
                    movieGenresLiveData.postValue(response.value)
                }
            }
        }
    }

    fun getTVGenres() {
        viewModelScope.launch {
            when (val response = tvRepository.getTVGenres(AppConfig.REGION)) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    Logger.i(response.value.toString())
                    tvGenresLiveData.postValue(response.value)
                }
            }
        }
    }
}
