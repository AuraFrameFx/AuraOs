object GenesisDependencies {

    object Android {
        const val core = "androidx.core:core-ktx:1.16.0"
        const val material = "com.google.android.material:material:1.12.0"
        const val activityCompose = "androidx.activity:activity-compose:1.10.1"
    }

    object Compose {
        const val bom = "androidx.compose:compose-bom:${GenesisVersions.composeBom}"
        const val ui = "androidx.compose.ui:ui"
        const val material3 = "androidx.compose.material3:material3"
        const val tooling = "androidx.compose.ui:ui-tooling"
        const val toolingPreview = "androidx.compose.ui:ui-tooling-preview"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${GenesisVersions.hilt}"
        const val compiler = "com.google.dagger:hilt-compiler:${GenesisVersions.hilt}"
        const val navigationCompose = "androidx.hilt:hilt-navigation-compose:1.2.0"
        const val testing = "com.google.dagger:hilt-android-testing:${GenesisVersions.hilt}"
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${GenesisVersions.retrofit}"
        const val retrofitSerialization = "com.squareup.retrofit2:converter-kotlinx-serialization:${GenesisVersions.retrofit}"
        const val okhttp = "com.squareup.okhttp3:okhttp:${GenesisVersions.okhttp}"
        const val okhttpLogging = "com.squareup.okhttp3:logging-interceptor:${GenesisVersions.okhttp}"
        const val serialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0"
    }

    object Room {
        const val runtime = "androidx.room:room-runtime:${GenesisVersions.room}"
        const val compiler = "androidx.room:room-compiler:${GenesisVersions.room}"
        const val ktx = "androidx.room:room-ktx:${GenesisVersions.room}"
    }

    // NAVIGATION - WAS MISSING
    object Navigation {
        const val compose = "androidx.navigation:navigation-compose:${GenesisVersions.navigation}"
        const val fragment = "androidx.navigation:navigation-fragment-ktx:${GenesisVersions.navigation}"
        const val ui = "androidx.navigation:navigation-ui-ktx:${GenesisVersions.navigation}"
        const val testing = "androidx.navigation:navigation-testing:${GenesisVersions.navigation}"
    }

    // C++ NDK NATIVE - WAS MISSING
    object Native {
        const val log = "android.log"  // Built-in Android logging
        const val jni = "android.jni"  // Built-in JNI support
        // Add custom native library dependencies here
    }

    object Testing {
        const val junit = "org.junit.jupiter:junit-jupiter:${GenesisVersions.junit}"
        const val junitEngine = "org.junit.jupiter:junit-jupiter-engine:${GenesisVersions.junit}"
        const val mockk = "io.mockk:mockk:${GenesisVersions.mockk}"
        const val turbine = "app.cash.turbine:turbine:${GenesisVersions.turbine}"
        const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${GenesisVersions.coroutines}"
    }
}
