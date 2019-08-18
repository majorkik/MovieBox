package com.majorik.moviebox.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.majorik.data.api.TmdbApiService
import com.majorik.data.api.TraktApiService
import com.majorik.domain.UrlConstants
import com.majorik.moviebox.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val tmdbNetworkModule = module {
    single { createTmdbWebService<TmdbApiService>() }
    single { createTraktWebService<TraktApiService>() }
}

fun getBaseOkHttpClient() = OkHttpClient()

fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    return httpLoggingInterceptor
}

fun createTmdbRequestInterceptor(): Interceptor = Interceptor { chain ->
    val url = chain.request()
        .url
        .newBuilder()
        .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
        .build()

    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()

    return@Interceptor chain.proceed(request)
}

fun createTraktRequestInterceptor(): Interceptor = Interceptor { chain ->
    val request = chain.request()
        .newBuilder()
        .addHeader("Content-type", "application/json")
        .addHeader("trakt-api-version", "2")
        .addHeader("trakt-api-key", BuildConfig.TRAKT_API_KEY)
        .build()

    return@Interceptor chain.proceed(request)
}

fun createTmdbOkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(createTmdbRequestInterceptor())
        .addInterceptor(getHttpLoggingInterceptor())
        .build()
}

fun createTraktOkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(createTraktRequestInterceptor())
        .addInterceptor(getHttpLoggingInterceptor())
        .build()
}

fun getTmdbRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(UrlConstants.TMDB_BASE_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

fun getTraktRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(UrlConstants.TRAKT_BASE_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

inline fun <reified T> createTmdbWebService(): T =
    getTmdbRetrofit(createTmdbOkHttpClient())
        .create(T::class.java)

inline fun <reified T> createTraktWebService(): T =
    getTraktRetrofit(createTraktOkHttpClient())
        .create(T::class.java)