package com.majorik.moviebox.data.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun getBaseOkHttpClient() = OkHttpClient()

/**
 * Base
 */
private val getHttpLoggingInterceptor = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}

/**
 * Tmdb V3
 */
private val getTmdbV3RequestInterceptor = Interceptor.invoke { chain ->
    val url = chain.request()
        .url
        .newBuilder()
        .addQueryParameter("api_key", BuildConfig.GRADLE_KEY_TMDB)
        .build()

    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()

    return@invoke chain.proceed(request)
}

fun createTmdbV3OkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(getTmdbV3RequestInterceptor)
        .addInterceptor(getHttpLoggingInterceptor)
        .build()
}

fun getTmdbV3Retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(UrlConstants.TMDB_BASE_URL_V3)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

inline fun <reified T> createTmdbV3WebService(): T =
    getTmdbV3Retrofit(createTmdbV3OkHttpClient())
        .create(T::class.java)

/**
 * Tmdb V4
 */
private val getTmdbV4RequestInterceptor = Interceptor.invoke { chain ->
    val request = chain.request()
        .newBuilder()
        .addHeader("Authorization", "Bearer ${BuildConfig.GRADLE_KEY_TMDBV4}")
        .addHeader("Content-Type", "application/json;charset=utf-8")
        .build()

    return@invoke chain.proceed(request)
}

fun createTmdbV4OkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(getTmdbV4RequestInterceptor)
        .addInterceptor(getHttpLoggingInterceptor)
        .build()
}

fun getTmdbV4Retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(UrlConstants.TMDB_BASE_URL_V4)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

inline fun <reified T> createTmdbV4WebService(): T =
    getTmdbV4Retrofit(createTmdbV4OkHttpClient())
        .create(T::class.java)

/**
 * Trakt
 */
private val getTraktRequestInterceptor = Interceptor.invoke { chain ->
    val request = chain.request()
        .newBuilder()
        .addHeader("Content-type", "application/json")
        .addHeader("trakt-api-version", "2")
        .addHeader("trakt-api-key", BuildConfig.GRADLE_KEY_TRAK_TV)
        .build()

    return@invoke chain.proceed(request)
}

fun createTraktOkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(getTraktRequestInterceptor)
        .addInterceptor(getHttpLoggingInterceptor)
        .build()
}

fun getTraktRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(UrlConstants.TRAKT_BASE_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

inline fun <reified T> createTraktWebService(): T =
    getTraktRetrofit(createTraktOkHttpClient())
        .create(T::class.java)

/**
 * Youtube
 */
private val getYoutubeRequestInterceptor = Interceptor.invoke { chain ->
    val request = chain.request()
        .newBuilder()
        .addHeader("Accept", "application/json")
        .build()

    return@invoke chain.proceed(request)
}

fun createYouTubeOkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(getYoutubeRequestInterceptor)
        .addInterceptor(getHttpLoggingInterceptor)
        .build()
}

fun getYoutubeRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(UrlConstants.YOUTUBE_BASE_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

inline fun <reified T> createYoutubeWebService(): T =
    getYoutubeRetrofit(createYouTubeOkHttpClient())
        .create(T::class.java)
