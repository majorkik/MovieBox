package com.majorik.moviebox.ui.person_details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.majorik.data.repositories.PersonRepository
import com.majorik.domain.tmdbModels.person.PersonDetails
import kotlinx.coroutines.launch

class PersonDetailsViewModel(private val personRepository: PersonRepository) : ViewModel() {
    var personDetailsLiveData = MutableLiveData<PersonDetails>()

    fun fetchPersonDetails(
        personId: Int,
        language: String?,
        appendToResponse: String?
    ) {
        viewModelScope.launch {
            val response = personRepository.getPersonById(personId, language, appendToResponse)

            response?.let { personDetailsLiveData.postValue(response) }
        }
    }
}
