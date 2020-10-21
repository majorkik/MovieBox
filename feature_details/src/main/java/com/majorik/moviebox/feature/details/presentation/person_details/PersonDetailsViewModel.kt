package com.majorik.moviebox.feature.details.presentation.person_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.moviebox.feature.details.domain.tmdbModels.person.PersonDetails
import com.majorik.moviebox.feature.details.data.repositories.PersonRepository
import com.majorik.library.base.models.results.ResultWrapper
import kotlinx.coroutines.launch

class PersonDetailsViewModel(private val personRepository: PersonRepository) : ViewModel() {
    var personDetailsLiveData = MutableLiveData<PersonDetails>()

    fun fetchPersonDetails(
        personId: Int,
        language: String?,
        appendToResponse: String?
    ) {
        viewModelScope.launch {
            when (val response = personRepository.getPersonById(personId, language, appendToResponse)) {
                is ResultWrapper.NetworkError -> {
                }

                is ResultWrapper.GenericError -> {
                }

                is ResultWrapper.Success -> {
                    personDetailsLiveData.postValue(response.value)
                }
            }
        }
    }
}
