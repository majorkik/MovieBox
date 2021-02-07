object AndroidConfig {
    const val compileSdk = 30
    const val minSdk = 23
    const val targetSdk = 30
    const val buildTools = "30.0.2"

    const val versionCode = 1
    const val versionName = "1.0"

    const val applicationId = "com.majorik.moviebox"
}

interface BuildType {

    companion object {
        const val RELEASE = "release"
        const val DEBUG = "debug"
    }

    val isMinifyEnabled: Boolean
}

object BuildTypeDebug : BuildType {
    override val isMinifyEnabled = false
}

object BuildTypeRelease : BuildType {
    override val isMinifyEnabled = false
}

object TestOptions {
    const val IS_RETURN_DEFAULT_VALUES = true
}
