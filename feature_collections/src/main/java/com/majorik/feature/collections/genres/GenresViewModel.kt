package com.majorik.feature.collections.genres

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.feature.collections.data.repositories.MovieRepository
import com.majorik.feature.collections.data.repositories.TVRepository
import com.majorik.feature.collections.domain.tmdbModels.genre.GenreResponse
import com.majorik.feature.collections.genres.GenresActivity.GenresType
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.launch

class GenresViewModel(
    private val movieRepository: MovieRepository,
    private val tvRepository: TVRepository
) : ViewModel() {
    var genresLiveData = MutableLiveData<GenreResponse>()

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

            when (response) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    genresLiveData.postValue(response.value)
                }
            }
        }
    }
}
