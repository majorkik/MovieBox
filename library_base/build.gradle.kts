plugins {
    id(Plugins.androidLibrary)
    kotlin(Plugins.android)
    id(Plugins.navSafeArgs)
}

android {
    compileSdkVersion(AndroidConfig.compileSdk)

    defaultConfig {
        minSdkVersion(AndroidConfig.minSdk)
        targetSdkVersion(AndroidConfig.targetSdk)

        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()

        freeCompilerArgs = freeCompilerArgs + listOf("-Xallow-jvm-ir-dependencies", "-Xskip-prerelease-check")
    }
}

dependencies {
    api(Libs.Kotlin.kotlinStdLib)
    api(Libs.Kotlin.kotlinReflect)
    api(Libs.Kotlin.kotlinxCoroutinesAndroid)

    api(Libs.Network.OkHttp3.OKHTTP_3)

    api(Libs.Network.Retrofit.retrofit)
    api(Libs.Network.Retrofit.retrofitMoshi)

    api(Libs.AndroidX.coreKTX)

    api(Libs.Koin.koinScope)
    api(Libs.Koin.koinFragment)
    api(Libs.Koin.koinViewModel)

    api(Libs.Loggers.timber)
    api(Libs.Loggers.prettyLogger)

    api(Libs.AndroidX.annotation)
    api(Libs.AndroidX.appCompat)
    api(Libs.AndroidX.fragmentKTX)
    api(Libs.AndroidX.activityKTX)
    api(Libs.AndroidX.recyclerView)
    api(Libs.AndroidX.viewPager2)
    api(Libs.AndroidX.vectorDrawable)
    api(Libs.AndroidX.constraintLayout)
    api(Libs.AndroidX.coordinatorLayout)
    api(Libs.AndroidX.viewPager2)
    api(Libs.AndroidX.pagingLibrary3)

    api(Libs.AndroidX.Navigation.navUiKtx)
    api(Libs.AndroidX.Navigation.navFragmentKTX)
    api(Libs.AndroidX.Navigation.navDynamicFragment)

    api(Libs.AndroidX.Lifecycle.lifecycleLiveDataKTX)
    api(Libs.AndroidX.Lifecycle.lifecycleRuntimeKTX)
    api(Libs.AndroidX.Lifecycle.lifecycleExt)
    api(Libs.AndroidX.Lifecycle.lifecycleViewModelKTX)

    api(Libs.Google.material)

    api(Libs.Others.viewBindingDelegates)
    api(Libs.Others.coil)
    api(Libs.Others.klock)
    api(Libs.Others.localization)

    api(Libs.Firebase.firebaseAnalytics)
    api(Libs.Firebase.firebaseCrashlytics)

    api(Libs.Others.lottie)
    api(Libs.Others.stfalconImageViewer)
    api(Libs.Others.recyclerViewAnimations)
}
