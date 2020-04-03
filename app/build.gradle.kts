import com.android.build.gradle.internal.dsl.BaseFlavor
import com.android.build.gradle.internal.dsl.DefaultConfig

plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
    id(GradlePluginId.KTLINT_GRADLE)
}

android {
    compileSdkVersion(AndroidConfig.COMPILE_SDK_VERSION)

    defaultConfig {
        applicationId = AndroidConfig.ID
        minSdkVersion(AndroidConfig.MIN_SDK_VERSION)
        targetSdkVersion(AndroidConfig.TARGET_SDK_VERSION)
        buildToolsVersion(AndroidConfig.BUILD_TOOLS_VERSION)

        versionCode = AndroidConfig.VERSION_CODE
        versionName = AndroidConfig.VERSION_NAME
        testInstrumentationRunner = AndroidConfig.TEST_INSTRUMENTATION_RUNNER

        buildConfigFieldFromGradleProperty("keyTmdb")
        buildConfigFieldFromGradleProperty("youTubeKey")
        buildConfigFieldFromGradleProperty("keyTrakTv")

        buildConfigField("FEATURE_MODULE_NAMES", getDynamicFeatureModuleNames())
    }

    buildTypes {
        getByName(BuildType.RELEASE) {
            isMinifyEnabled = BuildTypeRelease.isMinifyEnabled
            proguardFiles("proguard-android.txt", "proguard-rules.pro")
        }

        getByName(BuildType.DEBUG) {
            isMinifyEnabled = BuildTypeDebug.isMinifyEnabled
        }

        testOptions {
            unitTests.isReturnDefaultValues = TestOptions.IS_RETURN_DEFAULT_VALUES
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_1_8
            targetCompatibility = JavaVersion.VERSION_1_8
        }
    }

    // Each feature module that is included in settings.gradle.kts is added here as dynamic feature
    dynamicFeatures = ModuleDependency.getDynamicFeatureModules().toMutableSet()

    lintOptions {
        // By default lint does not check test sources, but setting this option means that lint will not even parse them
        isIgnoreTestSources = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    viewBinding {
        isEnabled = true
    }
}

dependencies {
    api(project(ModuleDependency.LIBRARY_BASE))

    api(LibraryDependency.NAVIGATION_FRAGMENT_KTX)
    api(LibraryDependency.NAVIGATION_UI_KTX)

    implementation(LibraryDependency.LOGGING_INTERCEPTOR)

    implementation(LibraryDependency.KOTLIN_COROUTINES_ADAPTER)

    api(LibraryDependency.CONSTRAINT_LAYOUT)
    api(LibraryDependency.COORDINATOR_LAYOUT)
    api(LibraryDependency.MATERIAL)
    api(LibraryDependency.VIEWPAGER_2)
    api(LibraryDependency.PAGING_RUNTIME)

    api(LibraryDependency.LOTTIE)

    api(LibraryDependency.STFALCON_IMAGEVIEWER)
    api(LibraryDependency.CIRCLE_IMAGEVIEW)

    api(LibraryDependency.RECYCLER_VIEW_ANIMATION)

    addTestDependencies()
}

fun BaseFlavor.buildConfigFieldFromGradleProperty(gradlePropertyName: String) {
    val propertyValue =
        com.android.build.gradle.internal.cxx.configure.gradleLocalProperties(rootDir)[gradlePropertyName] as? String
    checkNotNull(propertyValue) { "Gradle property $gradlePropertyName is null" }

    val androidResourceName = "GRADLE_${gradlePropertyName.toSnakeCase()}".toUpperCase()
    buildConfigField("String", androidResourceName, propertyValue)
}

fun getDynamicFeatureModuleNames() = ModuleDependency.getDynamicFeatureModules()
    .map { it.replace(":feature_", "") }
    .toSet()

fun String.toSnakeCase() = this.split(Regex("(?=[A-Z])")).joinToString("_") { it.toLowerCase() }

fun DefaultConfig.buildConfigField(name: String, value: Set<String>) {
    // Generates String that holds Java String Array code
    val strValue = value.joinToString(prefix = "{", separator = ",", postfix = "}", transform = { "\"$it\"" })
    buildConfigField("String[]", name, strValue)
}

//``apply plugin: 'com.android.application'
//
//apply plugin: 'kotlin-android'
//
//apply plugin: 'kotlin-android-extensions'
//
//apply plugin: 'kotlin-kapt'
//
//apply plugin: 'com.google.gms.google-services'
//
//apply plugin: 'com.google.firebase.crashlytics'
//
//apply plugin: 'com.google.firebase.appdistribution'
//
//androidExtensions {
//    experimental = true
//}
//
//android {
//    if (project.hasProperty('devBuild')) {
//        splits.abi.enable = false
//        splits.density.ebable = false
//        aaptOptions.cruncherEnabled = false
//    }
//
//    compileSdkVersion 29
//    defaultConfig {
//        applicationId "com.majorik.moviebox"
//        minSdkVersion 21
//        targetSdkVersion 29
//        versionCode 1
//        versionName "1.0"
//        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
//        multiDexEnabled true
//        vectorDrawables.useSupportLibrary = true
//    }
//    buildTypes {
//        release {
//            debuggable false
//            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//        }
//
//        debug {
//            firebaseAppDistribution {
//                serviceCredentialsFile = 'C:\\Users\\major\\Downloads\\firebase_moviebox.json'
//                testers = "majorik.tm@gmail.com"
//            }
//
//            debuggable true
//
//            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
//
//            ext.alwaysUpdateBuildId = false
//            ext.enableCrashlytics = false
//        }
//
//        buildTypes.each {
//            Properties properties = new Properties()
//            properties.load(project.rootProject.file("local.properties").newDataInputStream())
//            def keyTmdb = properties.getProperty("keyTmdb", "")
//            def keyTrakt = properties.getProperty("keyTrakTV", "")
//            def secretKeyTrakt = properties.getProperty("secretKeyTrakTV", "")
//            def youTubeKey = properties.getProperty("youTubeKey", "")
//
//            it.buildConfigField 'String', "TMDB_API_KEY", keyTmdb
//            it.buildConfigField 'String', "TRAKT_API_KEY", keyTrakt
//            it.buildConfigField 'String', "TRAKT_API_SECRET_KEY", secretKeyTrakt
//            it.buildConfigField 'String', "YOUTUBE_API_KEY", youTubeKey
//
//            it.resValue 'string', "tmdb_api_key", keyTmdb
//            it.resValue 'string', "trakt_api_key", keyTrakt
//            it.resValue 'string', "trakt_api_secret_key", secretKeyTrakt
//            it.resValue 'string', "youtube_api_key", youTubeKey
//        }
//    }
//
//    compileOptions {
//        sourceCompatibility JavaVersion.VERSION_1_8
//        targetCompatibility JavaVersion.VERSION_1_8
//    }
//
//    kotlinOptions {
//        jvmTarget = 1.8
//    }
//
//    dexOptions {
//        preDexLibraries true
//    }
//
//    viewBinding {
//        enabled = true
//    }
//    dynamicFeatures = [":feature_search", ":feature_collections", ":feature_details", ":feature_navigation"]
//}
//
//configurations.all {
//    resolutionStrategy {
//        force "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
//    }
//}
//
//dependencies {
//    def androidxDependencies = rootProject.ext.androidxDependencies
//    def kotlinDependencies = rootProject.ext.kotlinDependencies
//    def koinDependencies = rootProject.ext.koinDependencies
//    def networkDependencies = rootProject.ext.networkDependencies
//    def logDependencies = rootProject.ext.logDependencies
//    def otherDependencies = rootProject.ext.otherDependencies
//    def testingDependencies = rootProject.ext.testingDependencies
//    implementation project(':data')
//    implementation project(':domain')
//
//    implementation fileTree(dir: 'libs', include: ['*.jar'])
//
//    implementation androidxDependencies.material
//    implementation androidxDependencies.appCompat
//    implementation androidxDependencies.constraintLayout
//    implementation androidxDependencies.legacySupport
//    implementation androidxDependencies.lifecycleExtensions
//    implementation androidxDependencies.lifecycleRuntimeKtx
//    implementation androidxDependencies.lifecycleKtx
//    implementation androidxDependencies.annotation
//    implementation androidxDependencies.navigationFragment
//    implementation androidxDependencies.navigationUI
//    implementation androidxDependencies.navigationFragmentKtx
//    implementation androidxDependencies.navigationUIKtx
//    implementation androidxDependencies.pagingLibrary
//    implementation androidxDependencies.fragment
//    implementation androidxDependencies.viewpager2
//    implementation androidxDependencies.livedata
//
//    implementation kotlinDependencies.coreKtx
//    implementation kotlinDependencies.coroutinesCore
//    implementation kotlinDependencies.coroutinesAndroid
//
//    implementation koinDependencies.koinScope
//    implementation koinDependencies.koinViewModel
//    implementation koinDependencies.koinAndroidExt
//
//    implementation networkDependencies.retrofit2
//    implementation networkDependencies.retrofit2Moshi
//    implementation networkDependencies.okHttp3
//    implementation networkDependencies.okHttp3Logging
//    implementation networkDependencies.retrofitCoroutinesAdapter
//
//    implementation logDependencies.timber
//    implementation logDependencies.firebaseAnalytics
//    implementation logDependencies.firebaseCrashlytics
//    implementation logDependencies.prettyLogger
//
//    implementation otherDependencies.dotsIndicator
//    implementation otherDependencies.imageViewer
//    implementation otherDependencies.photoview
//    implementation otherDependencies.expandableText
//    implementation otherDependencies.circleImageView
//    implementation otherDependencies.floatingsearchview
//    implementation otherDependencies.rangeSeekBar
//    implementation otherDependencies.numberPicker
//    implementation otherDependencies.klock
//    implementation otherDependencies.lottie
//    implementation otherDependencies.localization
//    implementation otherDependencies.coil
//
//    implementation 'jp.wasabeef:recyclerview-animators:3.0.0'
//
//    implementation 'androidx.appcompat:appcompat:1.1.0'
//    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
//    implementation "org.jetbrains.kotlin:kotlin-reflect"
//
//    androidTestImplementation testingDependencies.testJunit
//    androidTestImplementation testingDependencies.testCore
//    androidTestImplementation testingDependencies.testRunner
//    androidTestImplementation testingDependencies.testRules
//    androidTestImplementation testingDependencies.mockWebServer
//}``
