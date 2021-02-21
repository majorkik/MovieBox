@file:Suppress("detekt.StringLiteralDuplication")

import Versions.coroutines
import Versions.kotlin

object Versions {
    const val androidGradle = "7.0.0-alpha05"

    const val kotlin = "1.4.30"
    const val coroutines = "1.4.2"

    const val navSafeArgs = "2.3.3"

    const val gradleVersions = "0.36.0"
    const val ktlint = "9.2.1"

    const val crashlytics = "2.4.1"
    const val gms = "4.3.4"
}

object Plugins {
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    const val androidApplication = "com.android.application"
    const val androidDynamicFeature = "com.android.dynamic-feature"
    const val androidLibrary = "com.android.library"
    const val gradleVersions = "com.github.ben-manes.versions"
    const val navSafeArgs = "androidx.navigation.safeargs.kotlin"
    const val crashlytics = "com.google.firebase.crashlytics"
    const val gms = "com.google.gms.google-services"
    const val android = "android"
}

object Libs {

    object Google {
        const val playCore = "com.google.android.play:core:1.7.3"
        const val material = "com.google.android.material:material:1.3.0"
    }

    object Kotlin {
        const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlin"
        const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlin"
    }

    object AndroidX {
        const val activityKTX = "androidx.activity:activity-ktx:1.2.0-alpha04"
        const val fragmentKTX = "androidx.fragment:fragment-ktx:1.3.0-alpha04"
        const val vectorDrawable = "androidx.vectordrawable:vectordrawable:1.1.0"
        const val pagingLibrary3 = "androidx.paging:paging-runtime:3.0.0-alpha13"
        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.0-alpha03"
        const val viewPager2 = "androidx.viewpager2:viewpager2:1.0.0"
        const val annotation = "androidx.annotation:annotation:1.1.0"
        const val multidex = "androidx.multidex:multidex:2.0.1"
        const val appCompat = "androidx.appcompat:appcompat:1.2.0"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.4"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:1.1.0"
        const val coreKTX = "androidx.core:core-ktx:1.3.2"

        object Lifecycle {
            private const val version = "2.2.0"

            const val lifecycleExt = "androidx.lifecycle:lifecycle-extensions:$version"
            const val lifecycleRuntimeKTX = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
            const val lifecycleViewModelKTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val lifecycleLiveDataKTX = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
        }

        object Navigation {
            private const val navVersion = "2.3.3"

            const val navFragmentKTX = "androidx.navigation:navigation-fragment-ktx:$navVersion"
            const val navUiKtx = "androidx.navigation:navigation-ui-ktx:$navVersion"
            const val navDynamicFragment = "androidx.navigation:navigation-dynamic-features-fragment:$navVersion"
        }

        object Compose {
            const val version = "1.0.0-alpha12"

            const val animation = "androidx.compose.animation:animation:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val layout = "androidx.compose.foundation:foundation-layout:$version"
            const val iconsExtended = "androidx.compose.material:material-icons-extended:$version"
            const val material = "androidx.compose.material:material:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val ui = "androidx.compose.ui:ui:$version"
            const val uiUtil = "androidx.compose.ui:ui-util:$version"

            const val paging = "androidx.paging:paging-compose:1.0.0-alpha07"
        }
    }

    object Loggers {
        const val timber = "com.jakewharton.timber:timber:4.7.1"
        const val prettyLogger = "com.orhanobut:logger:2.2.0"
    }

    object Koin {
        private const val version = "2.2.2"

        const val koinScope = "org.koin:koin-androidx-scope:$version"
        const val koinViewModel = "org.koin:koin-androidx-viewmodel:$version"
        const val koinFragment = "org.koin:koin-androidx-fragment:$version"
    }

    object Firebase {
        const val firebaseAnalytics = "com.google.firebase:firebase-analytics:18.0.0"
        const val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:17.3.0"
    }

    object Network {
        const val retrofitCoroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:0.9.2"

        object Retrofit {
            private const val version = "2.9.0"

            const val retrofit = "com.squareup.retrofit2:retrofit:$version"
            const val retrofitMoshi = "com.squareup.retrofit2:converter-moshi:$version"
        }

        object OkHttp3 {
            private const val version = "4.9.0"

            const val OKHTTP_3 = "com.squareup.okhttp3:okhttp:$version"
            const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:$version"
        }

    }

    object Others {
        const val stfalconImageViewer = "com.github.stfalcon:stfalcon-imageviewer:1.0.10"
        const val klock = "com.soywiz.korlibs.klock:klock:1.12.0"
        const val lottie = "com.airbnb.android:lottie:3.6.0"
        const val localization = "com.akexorcist:localization:1.2.6"
        const val coil = "io.coil-kt:coil:1.1.1"
        const val recyclerViewAnimations = "jp.wasabeef:recyclerview-animators:4.0.1"
        const val viewBindingDelegates = "com.github.kirich1409:ViewBindingPropertyDelegate:1.4.1"
        const val viewBindingDelegatesNoReflect = "com.kirich1409.viewbindingpropertydelegate:vbpd-noreflection:1.4.1"
        const val rangeSeekBar = "com.github.Jay-Goo:RangeSeekBar:3.0.0"
    }

    object Accompanist {
        private const val version = "0.5.1"

        const val coil = "dev.chrisbanes.accompanist:accompanist-coil:$version"
        const val insets = "dev.chrisbanes.accompanist:accompanist-insets:$version"
    }
}
