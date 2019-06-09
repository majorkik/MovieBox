package com.majorik.moviebox.di

import com.majorik.data.repositories.*
import org.koin.dsl.module

val repositoryModule = module {
    factory { AccountRepository(get()) }
    factory {  AuthRepository(get())}
    factory {  MovieRepository(get())}
    factory {  TVRepository(get())}
    factory {  PersonRepository(get())}
    factory {  SearchRepository(get())}
}