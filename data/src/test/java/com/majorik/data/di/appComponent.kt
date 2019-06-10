package com.majorik.data.di


fun appComponentTest(apiUrl: String) = listOf(
    networkModuleTest(apiUrl),
    repositoryModuleTest
)