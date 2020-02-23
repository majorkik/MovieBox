package com.majorik.moviebox.ui.person_details.filmography

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.PersonRepository
import com.majorik.domain.tmdbModels.person.PersonDetails
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class PersonCreditsViewModel(private val personRepository: PersonRepository) : BaseViewModel() {

    val movieCreditsLiveData = MutableLiveData<PersonDetails.MovieCredits>()
    val tvCreditsLiveData = MutableLiveData<PersonDetails.TVCredits>()

    fun fetchMovieCredits(personId: Int, language: String?) {
        ioScope.launch {
            val response = personRepository.getPersonMovieCredits(personId, language)

            response?.let { movieCreditsLiveData.postValue(it) }
        }
    }

    fun fetchTVCredits(personId: Int, language: String?) {
        ioScope.launch {
            val response = personRepository.getPersonTVCredits(personId, language)

            response?.let { tvCreditsLiveData.postValue(it) }
        }
    }
}