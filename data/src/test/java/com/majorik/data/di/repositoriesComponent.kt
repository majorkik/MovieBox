package com.majorik.data.di

import com.majorik.data.repositories.*
import org.koin.dsl.module

val repositoryModuleTest = module {
    factory { AccountRepository(get()) }
    factory {  AuthRepository(get()) }
    factory {  MovieRepository(get()) }
    factory {  TVRepository(get()) }
    factory {  PersonRepository(get()) }
    factory {  SearchRepository(get()) }
}