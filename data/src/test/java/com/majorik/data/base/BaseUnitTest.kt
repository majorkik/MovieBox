package com.majorik.data.base

import okhttp3.mockwebserver.MockResponse
import java.io.File


abstract class BaseUnitTest : BaseTest() {
    fun mockHttpResponse(fileName: String, responseCode: Int) = mockServer.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJson(fileName))
    )

    private fun getJson(path: String): String {
        val uri = BaseUnitTest::class.java.classLoader.getResource(path)
        return String(File(uri.path).readBytes())
    }
}