plugins {
    id(GradlePluginId.ANDROID_LIBRARY)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER
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
    }

    testOptions {
        unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
    }
}

dependencies {
    api(LibraryDependency.KOTLIN)
    api(LibraryDependency.KOTLIN_REFLECT)
    api(LibraryDependency.KOTLINX_COROUTINES_ANDROID)

    implementation(LibraryDependency.OKHTTP_3)

    api(LibraryDependency.RETROFIT)
    api(LibraryDependency.RETROFIT_MOSHI_CONVERTER)

    api(LibraryDependency.CORE_KTX)

    api(LibraryDependency.KOIN_SCOPE)
    api(LibraryDependency.KOIN_EXT)
    api(LibraryDependency.KOIN_FRAGMENT)
    api(LibraryDependency.KOIN_VIEWMODEL)

    api(LibraryDependency.TIMBER)
    api(LibraryDependency.PRETTY_LOGGER)

    api(LibraryDependency.APP_COMPAT)
    api(LibraryDependency.FRAGMENT_KTX)
    api(LibraryDependency.RECYCLERVIEW)
    api(LibraryDependency.VIEWPAGER_2)
    api(LibraryDependency.VECTOR_DRAWABLE)

    api(LibraryDependency.LIFECYCLE_EXT)
    api(LibraryDependency.LIFECYCLE_VIEWMODEL_KTX)

    api(LibraryDependency.COIL)

    api(LibraryDependency.KLOCK)

    api(LibraryDependency.FIREBASE_ANALYTICS)
    api(LibraryDependency.FIREBASE_CRASHLYTICS)

    api(LibraryDependency.LOCALIZATION)
}
