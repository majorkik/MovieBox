private object TestLibraryVersion {
    const val MOCK_WEBSERVER = "4.4.0"
    const val JUNIT = "4.13"
    const val TEST_CORE = "1.2.0"
    const val TEST_RULES = "1.2.0"
    const val TEST_JUNIT = "1.1.1"
    const val ESPRESSO_CORE = "3.2.0"
}

object TestLibraryDependency {
    const val MOCKWEBSERVER = "com.squareup.okhttp3:mockwebserver:${TestLibraryVersion.MOCK_WEBSERVER}" //testImpl
    const val JUNIT = "junit:junit:${TestLibraryVersion.JUNIT}"// implementation testImplementation
    const val JUNIT_EXT = "androidx.test.ext:junit:${TestLibraryVersion.TEST_JUNIT}" //androidTestImplementation
    const val ESPRESSO_CORE =
        "androidx.test.espresso:espresso-core:${TestLibraryVersion.ESPRESSO_CORE}" //androidTestImplementation
    const val TEST_CORE = "androidx.test:core:${TestLibraryVersion.TEST_CORE}"
    const val TEST_RUNNER = "androidx.test:runner:${TestLibraryVersion.TEST_RULES}"
    const val TEST_RULES = "androidx.test:rules:${TestLibraryVersion.TEST_RULES}"
}
