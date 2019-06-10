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
class SearchRepositoryTest : BaseUnitTest() {
    private val searchRepository by inject<SearchRepository>()

    override fun isMockServerEnabled() = true

    override fun setUp() {
        super.setUp()
        startKoin {
            modules(appComponentTest(getMockUrl()))
        }
    }

    @Test
    fun multiSearch() {
        mockHttpResponse("searchRepository/multiSearch.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val result = searchRepository.multiSearch("", "", 0, false)
            assertNotNull(result)
        }
    }

    @Test
    fun searchMovies() {
        mockHttpResponse("searchRepository/searchMovies.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val result = searchRepository.searchMovies("", "", 0, false, "", 0, 0)
            assertNotNull(result)
        }
    }

    @Test
    fun searchTVs() {
        mockHttpResponse("searchRepository/searchTVs.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val result = searchRepository.searchTVs("", "", 0, 0)
            assertNotNull(result)
        }
    }

    @Test
    fun searchPeoples() {
        mockHttpResponse("searchRepository/searchPerson.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val result = searchRepository.searchPeoples("", "", 0, false, "")
            assertNotNull(result)
        }
    }
}