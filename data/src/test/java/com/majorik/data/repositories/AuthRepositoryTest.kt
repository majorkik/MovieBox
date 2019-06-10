package com.majorik.data.repositories

import com.majorik.data.base.BaseUnitTest
import com.majorik.data.di.appComponentTest
import com.majorik.domain.models.request.RequestSession
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class AuthRepositoryTest : BaseUnitTest() {
    private val authRepository by inject<AuthRepository>()

    override fun isMockServerEnabled() = true

    override fun setUp() {
        super.setUp()
        startKoin {
            modules(appComponentTest(getMockUrl()))
        }
    }

    @Test
    fun createSession() {
        mockHttpResponse("authRepository/createSession.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val responseApi = authRepository.createSession("fake")
            assertNotNull(responseApi)
        }
    }

    @Test
    fun getRequestToken() {
        mockHttpResponse("authRepository/createRequestToken.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val responseApi = authRepository.getRequestToken()
            assertNotNull(responseApi)
        }
    }

    @Test
    fun deleteSession() {
        mockHttpResponse("authRepository/deleteSession.json", HttpURLConnection.HTTP_OK)
        runBlocking {
            val responseApi = authRepository.deleteSession(RequestSession("fake"))
            assertNotNull(responseApi)
        }
    }
}