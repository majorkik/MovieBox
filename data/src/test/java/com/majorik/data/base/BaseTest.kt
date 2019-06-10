package com.majorik.data.base

import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest


abstract class BaseTest : KoinTest {
    protected lateinit var mockServer: MockWebServer

    @Before
    open fun setUp() {
        configureMockServer()
    }

    @After
    open fun tearDown() {
        stopMockServer()
        stopKoin()
    }

    abstract fun isMockServerEnabled(): Boolean

    private fun configureMockServer() {
        if (isMockServerEnabled()) {
            mockServer = MockWebServer()
            mockServer.start()
        }
    }

    private fun stopMockServer() {
        if(isMockServerEnabled()) {
            mockServer.shutdown()
        }
    }

    fun getMockUrl() = mockServer.url("/").toString()
}