@file:Suppress("detekt.StringLiteralDuplication")

private object LibraryVersion {
    const val ACTIVITY_KTX = "1.2.0-alpha04"
    const val FRAGMENT_KTX = "1.3.0-alpha04"
    const val COORDINATOR_LAYOUT = "1.1.0"
    const val CONSTRAINT_LAYOUT = "2.0.0-beta4"
    const val PAGING_LIBRARY = "2.1.2"
    const val APP_COMPAT = "1.2.0-alpha03"
    const val MATERIAL = "1.2.0-alpha05"
    const val MULTIDEX = "1.0.3"
    const val NAVIGATION_DYNAMIC_FEATURE_FRAGMENT = "2.3.0-alpha05"

    const val PLAY_CORE = "1.7.3"

    const val LEGACY_SUPPORT = "1.0.0"
    const val LIFECYCLE_EXT = "2.2.0"
    const val LIFECYCLE_KTX = "2.2.0"
    const val ANNOTATION = "1.1.0"
    const val VECTOR_DRAWABLE = "1.1.0"
    const val NAVIGATION = "2.2.2"
    const val RECYCLERVIEW = "1.2.0-alpha03"
    const val SUPPORT_DESIGN = "28.0.0-rc02"
    const val VIEWPAGER_2 = "1.0.0"

    const val KOTLIN_KTX = "1.2.0"

    const val RETROFIT = "2.9.0"
    const val RETROFIT_COROUTINES = "0.9.2"
    const val OKHTTP3 = "4.7.2"

    const val TIMBER = "4.7.1"
    const val PRETTY_LOGGER = "2.2.0"

    const val KOIN = "2.1.5"

    const val STFALCON = "1.0.10"
    const val PHOTOVIEW = "2.3.0"
    const val CIRCLE_IMAGE = "3.1.0"
    const val KLOCK = "1.8.4"
    const val LOTTIE = "3.4.0"
    const val LOCALIZATION = "1.2.5"
    const val COIL = "0.11.0"
    const val RECYCLER_VIEW_ANIMATION = "3.0.0"
    const val VIEWBINDING_DELEGATES_VERSION = "0.3"

    const val FIREBASE_ANALYTICS = "17.4.2"
    const val FIREBASE_CRASHLYTICS = "17.0.0"

}

object LibraryDependency {
    /**
     * Kotlin
     */
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${CoreVersion.KOTLIN}"
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${CoreVersion.KOTLIN}"
    const val KOTLIN_STDLIB_JDK_8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${CoreVersion.KOTLIN}"
    const val CORE_KTX = "androidx.core:core-ktx:${LibraryVersion.KOTLIN_KTX}"
    const val KOTLINX_COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${CoreVersion.COROUTINES_ANDROID}"
    const val KOTLINX_COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${CoreVersion.COROUTINES_ANDROID}"
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${CoreVersion.KOTLIN}"

    /**
     * Network
     */
    const val RETROFIT = "com.squareup.retrofit2:retrofit:${LibraryVersion.RETROFIT}"
    const val RETROFIT_MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${LibraryVersion.RETROFIT}"
    const val OKHTTP_3 = "com.squareup.okhttp3:okhttp:${LibraryVersion.OKHTTP3}"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${LibraryVersion.OKHTTP3}"
    const val KOTLIN_COROUTINES_ADAPTER =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${LibraryVersion.RETROFIT_COROUTINES}"

    const val SUPPORT_DESIGN = "androidx.support:design:${LibraryVersion.SUPPORT_DESIGN}"
    const val MATERIAL = "com.google.android.material:material:${LibraryVersion.MATERIAL}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${LibraryVersion.APP_COMPAT}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${LibraryVersion.CONSTRAINT_LAYOUT}"
    const val LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:${LibraryVersion.LEGACY_SUPPORT}"
    const val COORDINATOR_LAYOUT = "androidx.coordinatorlayout:coordinatorlayout:${LibraryVersion.COORDINATOR_LAYOUT}"

    /**
     * Lifecycle
     */
    const val LIFECYCLE_EXT = "androidx.lifecycle:lifecycle-extensions:${LibraryVersion.LIFECYCLE_EXT}"
    const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${LibraryVersion.LIFECYCLE_KTX}"
    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersion.LIFECYCLE_KTX}"
    const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersion.LIFECYCLE_KTX}"

    /**
     * Navigation
     */
    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${LibraryVersion.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${LibraryVersion.NAVIGATION}"
    const val DYNAMIC_FEATURE_FRAGMENT =
        "androidx.navigation:navigation-dynamic-features-fragment:${LibraryVersion.NAVIGATION_DYNAMIC_FEATURE_FRAGMENT}"

    /**
     * Androidx
     */
    const val VECTOR_DRAWABLE = "androidx.vectordrawable:vectordrawable:${LibraryVersion.VECTOR_DRAWABLE}"
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${LibraryVersion.PAGING_LIBRARY}"
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:${LibraryVersion.RECYCLERVIEW}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${LibraryVersion.FRAGMENT_KTX}"
    const val ACTIVITY_KTX = "androidx.activity:activity-ktx:${LibraryVersion.ACTIVITY_KTX}"
    const val VIEWPAGER_2 = "androidx.viewpager2:viewpager2:${LibraryVersion.VIEWPAGER_2}"
    const val PLAY_CORE = "com.google.android.play:core:${LibraryVersion.PLAY_CORE}"
    const val ANNOTATION = "androidx.annotation:annotation:${LibraryVersion.ANNOTATION}"
    const val MULTIDEX = "com.android.support:multidex:${LibraryVersion.MULTIDEX}"

    /**
     * Koin
     */
    const val KOIN_SCOPE = "org.koin:koin-androidx-scope:${LibraryVersion.KOIN}"
    const val KOIN_VIEWMODEL = "org.koin:koin-androidx-viewmodel:${LibraryVersion.KOIN}"
    const val KOIN_FRAGMENT = "org.koin:koin-androidx-fragment:${LibraryVersion.KOIN}"
    const val KOIN_EXT = "org.koin:koin-androidx-ext:${LibraryVersion.KOIN}"
    const val KOIN_TEST = "org.koin:koin-test:${LibraryVersion.KOIN}" //testImpl

    /**
     * Log
     */
    const val TIMBER = "com.jakewharton.timber:timber:${LibraryVersion.TIMBER}"
    const val PRETTY_LOGGER = "com.orhanobut:logger:${LibraryVersion.PRETTY_LOGGER}"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics:${LibraryVersion.FIREBASE_ANALYTICS}"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics:${LibraryVersion.FIREBASE_CRASHLYTICS}"

    /**
     * Other libraries
     */
    const val STFALCON_IMAGEVIEWER = "com.github.stfalcon:stfalcon-imageviewer:${LibraryVersion.STFALCON}"
    const val PHOTOVIEW = "com.github.chrisbanes:PhotoView:${LibraryVersion.PHOTOVIEW}"
    const val CIRCLE_IMAGEVIEW = "de.hdodenhof:circleimageview:${LibraryVersion.CIRCLE_IMAGE}"
    const val KLOCK = "com.soywiz.korlibs.klock:klock:${LibraryVersion.KLOCK}"
    const val LOTTIE = "com.airbnb.android:lottie:${LibraryVersion.LOTTIE}"
    const val LOCALIZATION = "com.akexorcist:localization:${LibraryVersion.LOCALIZATION}"
    const val COIL = "io.coil-kt:coil:${LibraryVersion.COIL}"
    const val RECYCLER_VIEW_ANIMATION = "jp.wasabeef:recyclerview-animators:${LibraryVersion.RECYCLER_VIEW_ANIMATION}"
    const val VIEWBINDING_DELEGATES = "com.github.kirich1409:ViewBindingPropertyDelegate:${LibraryVersion.VIEWBINDING_DELEGATES_VERSION}"
}
