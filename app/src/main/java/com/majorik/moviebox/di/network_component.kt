package com.majorik.moviebox.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.majorik.data.api.TmdbApiService
import com.majorik.data.api.TraktApiService
import com.majorik.data.api.YouTubeApiService
import com.majorik.domain.constants.UrlConstants
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
    single { createYoutubeWebService<YouTubeApiService>() }
}

fun getBaseOkHttpClient() = OkHttpClient()

private val getHttpLoggingInterceptor = run {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.apply {
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    }
}

private val getTmdbRequestInterceptor = Interceptor.invoke { chain ->
    val url = chain.request()
        .url
        .newBuilder()
        .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
        .build()

    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()

    return@invoke chain.proceed(request)
}

private val getTraktRequestInterceptor = Interceptor.invoke { chain ->
    val request = chain.request()
        .newBuilder()
        .addHeader("Content-type", "application/json")
        .addHeader("trakt-api-version", "2")
        .addHeader("trakt-api-key", BuildConfig.TRAKT_API_KEY)
        .build()

    return@invoke chain.proceed(request)
}

private val getYoutubeRequestInterceptor = Interceptor.invoke { chain ->
    val request = chain.request()
        .newBuilder()
        .addHeader("Accept", "application/json")
        .build()

    return@invoke chain.proceed(request)
}

fun createTmdbOkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(getTmdbRequestInterceptor)
        .addInterceptor(getHttpLoggingInterceptor)
        .build()
}

fun createTraktOkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(getTraktRequestInterceptor)
        .addInterceptor(getHttpLoggingInterceptor)
        .build()
}

fun createYouTubeOkHttpClient(): OkHttpClient {
    return getBaseOkHttpClient()
        .newBuilder()
        .addNetworkInterceptor(getYoutubeRequestInterceptor)
        .addInterceptor(getHttpLoggingInterceptor)
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

fun getYoutubeRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl(UrlConstants.YOUTUBE_BASE_URL)
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

inline fun <reified T> createTmdbWebService(): T =
    getTmdbRetrofit(createTmdbOkHttpClient())
        .create(T::class.java)

inline fun <reified T> createTraktWebService(): T =
    getTraktRetrofit(createTraktOkHttpClient())
        .create(T::class.java)

inline fun <reified T> createYoutubeWebService(): T =
    getYoutubeRetrofit(createYouTubeOkHttpClient())
        .create(T::class.java)
