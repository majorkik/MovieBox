import com.android.build.gradle.internal.dsl.BaseFlavor
import com.android.build.gradle.internal.dsl.DefaultConfig

plugins {
    id(GradlePluginId.ANDROID_APPLICATION)
    id(GradlePluginId.KOTLIN_ANDROID)
    id(GradlePluginId.KOTLIN_ANDROID_EXTENSIONS)
    id(GradlePluginId.KTLINT_GRADLE)
    id("kotlin-android")
    id("kotlin-android-extensions")
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
        buildConfigFieldFromGradleProperty("keyTmdbv4")
        buildConfigFieldFromGradleProperty("youTubeKey")
        buildConfigFieldFromGradleProperty("keyTrakTv")

        buildConfigField("FEATURE_MODULE_NAMES", getDynamicFeatureModuleNames())

        multiDexEnabled = true
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

//        android.buildFeatures.viewBinding = true
    }

    viewBinding {
        isEnabled = true
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
}

dependencies {
    api(project(ModuleDependency.LIBRARY_BASE))

    api(LibraryDependency.NAVIGATION_FRAGMENT_KTX)
    api(LibraryDependency.NAVIGATION_UI_KTX)

    implementation(LibraryDependency.MULTIDEX)
    implementation(LibraryDependency.PLAY_CORE)

    implementation(LibraryDependency.LOGGING_INTERCEPTOR)

    implementation(LibraryDependency.KOTLIN_COROUTINES_ADAPTER)

    api(LibraryDependency.CONSTRAINT_LAYOUT)
    api(LibraryDependency.COORDINATOR_LAYOUT)
    api(LibraryDependency.VIEWPAGER_2)
    api(LibraryDependency.PAGING_RUNTIME)

    api(LibraryDependency.LOTTIE)

    api(LibraryDependency.PHOTOVIEW)
    api(LibraryDependency.STFALCON_IMAGEVIEWER)
    api(LibraryDependency.CIRCLE_IMAGEVIEW)

    api(LibraryDependency.RECYCLER_VIEW_ANIMATION)
    api("androidx.appcompat:appcompat:1.1.0")
    implementation("org.jetbrains.kotlin:kotlin-stdlib:${CoreVersion.KOTLIN}")
    api("androidx.constraintlayout:constraintlayout:1.1.3")

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
    val strValue = value.joinToString(prefix = "{", separator = ",", postfix = "}", transform = {
        "\"$it\""
    })
    buildConfigField("String[]", name, strValue)
}
