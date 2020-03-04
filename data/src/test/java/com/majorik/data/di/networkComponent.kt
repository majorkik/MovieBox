package com.majorik.data.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.majorik.data.api.TmdbApiService
import okhttp3.Interceptor
import okhttp3.Interceptor.Companion.invoke
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun networkModuleTest(apiURL: String) = module {
    single { createOkHttpClient() }
    single { createWebService<TmdbApiService>(apiURL) }
}

fun createRequestInterceptor(): Interceptor = invoke { chain ->
    val url = chain.request()
        .url
        .newBuilder()
        .addQueryParameter("api_key", "fake")
        .build()

    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()

    return@invoke chain.proceed(request)
}

fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addNetworkInterceptor(createRequestInterceptor())
    .build()

fun getRetrofit(apiURL: String, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(apiURL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

inline fun <reified T> createWebService(apiURL: String): T =
    getRetrofit(
        apiURL,
        createOkHttpClient()
    ).create(T::class.java)
