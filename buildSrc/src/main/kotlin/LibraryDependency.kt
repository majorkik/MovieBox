@file:Suppress("detekt.StringLiteralDuplication")

private object LibraryVersion {
    const val FRAGMENT_KTX = "1.2.3"
    const val COORDINATOR_LAYOUT = "1.1.0"
    const val CONSTRAINT_LAYOUT = "2.0.0-beta4"
    const val PAGING_LIBRARY = "2.1.2"
    const val APP_COMPAT = "1.2.0-alpha03"
    const val MATERIAL = "1.2.0-alpha05"

    const val LEGACY_SUPPORT = "1.0.0"
    const val LIFECYCLE_EXT = "2.2.0"
    const val LIFECYCLE_KTX = "2.2.0"
    const val ANNOTATION = "1.1.0"
    const val VECTOR_DRAWABLE = "1.1.0"
    const val NAVIGATION = "2.2.1"
    const val RECYCLERVIEW = "1.2.0-alpha01"
    const val SUPPORT_DESIGN = "28.0.0-rc02"
    const val VIEWPAGER_2 = "1.0.0"

    const val KOTLIN_KTX = "1.2.0"

    const val MOSHI = "2.7.1"

    const val RETROFIT = "2.7.1"
    const val RETROFIT_COROUTINES = "0.9.2"
    const val OKHTTP3 = "4.4.0"

    const val TIMBER = "4.7.1"
    const val PRETTY_LOGGER = "2.2.0"

    const val KOIN = "2.1.5"

    const val VIEWPAGER_DOTS_INDICATOR = "3.0.3"
    const val STFALCON = "0.1.0"
    const val PHOTOVIEW = "2.3.0"
    const val EXPANDABLE_TEXT = "0.1.4"
    const val CIRCLE_IMAGE = "3.1.0"
    const val FLOATING_SEARCHVIEW = "2.1.1"
    const val RANGE_SEEK_BAR = "3.0.0"
    const val NUMBER_PICKER = "1.0.1"
    const val KLOCK = "1.8.4"
    const val LOTTIE = "3.4.0"
    const val LOCALIZATION = "1.2.4"
    const val COIL = "0.9.5"
    const val RECYCLER_VIEW_ANIMATION = "3.0.0"

    const val FIREBASE_ANALYTICS = "17.2.3"
    const val FIREBASE_CRASHLYTICS = "17.0.0-beta01"

    const val SLF4J_API = "1.7.25"
    const val SLF4J_TIMBER = "1.0.1"
}

object LibraryDependency {
    const val KOTLIN = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${CoreVersion.KOTLIN}"

    // Required by Android dynamic feature modules and SafeArgs
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${CoreVersion.KOTLIN}"

    const val RETROFIT = "com.squareup.retrofit2:retrofit:${LibraryVersion.RETROFIT}"
    const val RETROFIT_MOSHI_CONVERTER = "com.squareup.retrofit2:converter-moshi:${LibraryVersion.RETROFIT}"

    const val SUPPORT_DESIGN = "androidx.support:design:${LibraryVersion.SUPPORT_DESIGN}"
    const val MATERIAL = "com.google.android.material:material:${LibraryVersion.MATERIAL}"
    const val APP_COMPAT = "androidx.appcompat:appcompat:${LibraryVersion.APP_COMPAT}"
    const val CONSTRAINT_LAYOUT = "androidx.constraintlayout:constraintlayout:${LibraryVersion.CONSTRAINT_LAYOUT}"
    const val LEGACY_SUPPORT = "androidx.legacy:legacy-support-v4:${LibraryVersion.LEGACY_SUPPORT}"
    const val COORDINATOR_LAYOUT = "androidx.coordinatorlayout:coordinatorlayout:${LibraryVersion.COORDINATOR_LAYOUT}"

    const val LIFECYCLE_EXT = "androidx.lifecycle:lifecycle-extensions:${LibraryVersion.LIFECYCLE_EXT}"
    const val LIFECYCLE_RUNTIME_KTX = "androidx.lifecycle:lifecycle-runtime-ktx:${LibraryVersion.LIFECYCLE_KTX}"
    const val LIFECYCLE_VIEWMODEL_KTX = "androidx.lifecycle:lifecycle-viewmodel-ktx:${LibraryVersion.LIFECYCLE_KTX}"
    const val LIFECYCLE_LIVEDATA_KTX = "androidx.lifecycle:lifecycle-livedata-ktx:${LibraryVersion.LIFECYCLE_KTX}"

    const val ANNOTATION = "androidx.annotation:annotation:${LibraryVersion.ANNOTATION}"

    const val NAVIGATION_FRAGMENT_KTX = "androidx.navigation:navigation-fragment-ktx:${LibraryVersion.NAVIGATION}"
    const val NAVIGATION_UI_KTX = "androidx.navigation:navigation-ui-ktx:${LibraryVersion.NAVIGATION}"

    const val VECTOR_DRAWABLE = "androidx.vectordrawable:vectordrawable:${LibraryVersion.VECTOR_DRAWABLE}"
    const val PAGING_RUNTIME = "androidx.paging:paging-runtime:${LibraryVersion.PAGING_LIBRARY}"
    const val RECYCLERVIEW = "androidx.recyclerview:recyclerview:${LibraryVersion.RECYCLERVIEW}"
    const val FRAGMENT_KTX = "androidx.fragment:fragment-ktx:${LibraryVersion.FRAGMENT_KTX}"
    const val VIEWPAGER_2 = "androidx.viewpager2:viewpager2:${LibraryVersion.VIEWPAGER_2}"

    const val OKHTTP_3 = "com.squareup.okhttp3:okhttp:${LibraryVersion.OKHTTP3}"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:${LibraryVersion.OKHTTP3}"
    const val KOTLIN_COROUTINES_ADAPTER =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${LibraryVersion.RETROFIT_COROUTINES}"

    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib:${CoreVersion.KOTLIN}"
    const val KOTLIN_STDLIB_JDK_8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${CoreVersion.KOTLIN}"
    const val CORE_KTX = "androidx.core:core-ktx:${LibraryVersion.KOTLIN_KTX}"
    const val KOTLINX_COROUTINES_CORE =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${CoreVersion.COROUTINES_ANDROID}"
    const val KOTLINX_COROUTINES_ANDROID =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${CoreVersion.COROUTINES_ANDROID}"

    const val KOIN_SCOPE = "org.koin:koin-androidx-scope:${LibraryVersion.KOIN}"
    const val KOIN_VIEWMODEL = "org.koin:koin-androidx-viewmodel:${LibraryVersion.KOIN}"
    const val KOIN_FRAGMENT = "org.koin:koin-androidx-fragment:${LibraryVersion.KOIN}"
    const val KOIN_EXT = "org.koin:koin-androidx-ext:${LibraryVersion.KOIN}"
    const val KOIN_TEST = "org.koin:koin-test:${LibraryVersion.KOIN}" //testImpl

    const val TIMBER = "com.jakewharton.timber:timber:${LibraryVersion.TIMBER}"
    const val PRETTY_LOGGER = "com.orhanobut:logger:${LibraryVersion.PRETTY_LOGGER}"
    const val FIREBASE_ANALYTICS = "com.google.firebase:firebase-analytics:${LibraryVersion.FIREBASE_ANALYTICS}"
    const val FIREBASE_CRASHLYTICS = "com.google.firebase:firebase-crashlytics:${LibraryVersion.FIREBASE_CRASHLYTICS}"

    const val VIEWPAGER_DOTS_INDICATOR =
        "com.tbuonomo.andrui:viewpagerdotsindicator:${LibraryVersion.VIEWPAGER_DOTS_INDICATOR}"
    const val STFALCON_IMAGEVIEWER = "com.github.stfalcon:stfalcon-imageviewer:${LibraryVersion.STFALCON}"
    const val PHOTOVIEW = "com.github.chrisbanes:PhotoView:${LibraryVersion.PHOTOVIEW}"
    const val EXPANDABLE_TEXTVIEW = "com.ms-square:expandableTextView:${LibraryVersion.EXPANDABLE_TEXT}"
    const val CIRCLE_IMAGEVIEW = "de.hdodenhof:circleimageview:${LibraryVersion.CIRCLE_IMAGE}"
    const val FLOATING_SEARCHVIEW = "com.github.arimorty:floatingsearchview:${LibraryVersion.FLOATING_SEARCHVIEW}"
    const val RANGE_SEEK_BAR = "com.github.Jay-Goo:RangeSeekBar:${LibraryVersion.RANGE_SEEK_BAR}"
    const val NUMBER_PICKER = "com.super_rabbit.wheel_picker:NumberPicker:${LibraryVersion.NUMBER_PICKER}"
    const val KLOCK = "com.soywiz.korlibs.klock:klock:${LibraryVersion.KLOCK}"
    const val LOTTIE = "com.airbnb.android:lottie:${LibraryVersion.LOTTIE}"
    const val LOCALIZATION = "com.akexorcist:localization:${LibraryVersion.LOCALIZATION}"
    const val COIL = "io.coil-kt:coil:${LibraryVersion.COIL}"
    const val RECYCLER_VIEW_ANIMATION = "jp.wasabeef:recyclerview-animators:${LibraryVersion.RECYCLER_VIEW_ANIMATION}"
}
