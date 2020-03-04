package com.majorik.data.repositories

import com.majorik.data.base.BaseUnitTest
import com.majorik.data.di.appComponentTest
import java.net.HttpURLConnection
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject

@RunWith(JUnit4::class)
class TVRepositoryTest : BaseUnitTest() {
    private val tvRepository by inject<TVRepository>()

    override fun isMockServerEnabled() = true

    override fun setUp() {
        super.setUp()
        startKoin {
            modules(appComponentTest(getMockUrl()))
        }
    }

    @Test
    fun getTVById() {
        mockHttpResponse("tvRepository/tv.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val tv = tvRepository.getTVById(0, "", "", "")
            assertNotNull(tv)
        }
    }

    @Test
    fun getPopularTVs() {
        mockHttpResponse("tvRepository/popularTVs.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val popularTVs = tvRepository.getPopularTVs("", 0)
            assertNotNull(popularTVs)
        }
    }

    @Test
    fun getTopRatedTVs() {
        mockHttpResponse("tvRepository/topRatedTVs.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val topRatedTVs = tvRepository.getTVById(0, "", "", "")
            assertNotNull(topRatedTVs)
        }
    }

    @Test
    fun getTVGenres() {
        mockHttpResponse("tvRepository/tvGenres.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val tvGenres = tvRepository.getTVGenres("")
            assertNotNull(tvGenres)
        }
    }

    @Test
    fun getAccountStatesForTV() {
        mockHttpResponse("tvRepository/accountStatesTVs.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val accountStates = tvRepository.getAccountStatesForTV(0, "", "", "")
            assertNotNull(accountStates)
        }
    }
}
