package com.majorik.moviebox.ui.person

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.PersonRepository
import com.majorik.domain.tmdbModels.person.PersonDetails
import com.majorik.moviebox.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class PersonDetailsViewModel(private val personRepository: PersonRepository) : BaseViewModel() {
    var personDetailsLiveData = MutableLiveData<PersonDetails>()

    fun fetchPersonDetails(
        personId: Int,
        language: String?,
        appendToResponse: String?
    ) {
        ioScope.launch {
            val response = personRepository.getPersonById(personId, language, appendToResponse)

            response?.let { personDetailsLiveData.postValue(response) }
        }
    }
}