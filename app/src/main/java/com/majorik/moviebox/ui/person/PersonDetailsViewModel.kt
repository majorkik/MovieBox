package com.majorik.moviebox.ui.person

import androidx.lifecycle.MutableLiveData
import com.majorik.data.repositories.PersonRepository
import com.majorik.domain.models.person.PersonDetails
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
            val personDetails = personRepository.getPersonById(personId, language, appendToResponse)

            personDetailsLiveData.postValue(personDetails)
        }
    }
}