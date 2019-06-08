package com.majorik.moviebox.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.majorik.data.api.TmdbApiService
import com.majorik.moviebox.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single { createOkHttpClient() }
    single { createWebService<TmdbApiService>() }
}

fun createRequestInterceptor(): Interceptor = Interceptor { chain ->
    val url = chain.request()
        .url()
        .newBuilder()
        .addQueryParameter("api_key", BuildConfig.TMDB_API_KEY)
        .build()

    val request = chain.request()
        .newBuilder()
        .url(url)
        .build()

    return@Interceptor chain.proceed(request)
}

fun createOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addNetworkInterceptor(createRequestInterceptor())
    .build()

fun getRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
    .client(okHttpClient)
    .baseUrl("https://api.themoviedb.org/3/")
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

inline fun <reified T> createWebService(): T =
    getRetrofit(createOkHttpClient())
        .create(T::class.java)