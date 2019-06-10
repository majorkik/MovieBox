package com.majorik.data.repositories

import com.majorik.data.base.BaseUnitTest
import com.majorik.data.di.appComponentTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class PersonRepositoryTest : BaseUnitTest() {
    private val personRepository by inject<PersonRepository>()

    override fun isMockServerEnabled() = true

    override fun setUp() {
        super.setUp()
        startKoin {
            modules(appComponentTest(getMockUrl()))
        }
    }

    @Test
    fun getPersonById() {
        mockHttpResponse("personRepository/person.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val person = personRepository.getPersonById(0, "", "")
            assertNotNull(person)
        }
    }

    @Test
    fun getPersonTaggedImages() {
        mockHttpResponse("personRepository/personTaggedImages.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val personTaggedImages = personRepository.getPersonTaggedImages(0,"",0)
            assertNotNull(personTaggedImages)
        }
    }

    @Test
    fun getPersonPosters() {
        mockHttpResponse("personRepository/personPosters.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val personPosters = personRepository.getPersonPosters(0)
            assertNotNull(personPosters)
        }
    }
}