plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    // alias(libs.plugins.google.services) // Uncomment if defined in [plugins] in libs.versions.toml
    // alias(libs.plugins.firebase.crashlytics) // Uncomment if defined in [plugins]
    // alias(libs.plugins.firebase.perf) // Uncomment if defined in [plugins]
    alias(libs.plugins.spotless)
    alias(libs.plugins.openapi.generator)
}

// Apply OpenAPI generator configuration
apply(from = "$rootDir/openapi-generator.gradle.kts")

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = libs.versions.compileSdk.get().toInt()
    ndkVersion = libs.versions.ndkVersion.get().toString() // Ensure String type

    defaultConfig {
        applicationId = "dev.aurakai.auraframefx"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        // Genesis Protocol - Native AI Processing
        externalNativeBuild {
            cmake {
                cppFlags += "-std=c++17"
                abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64")
                arguments += listOf(
                    "-DANDROID_STL=c++_shared",
                    "-DCMAKE_VERBOSE_MAKEFILE=ON"
                )
                version = libs.versions.cmakeVersion.get().toString() // Ensure String type
            }
        }

        // NDK Configuration for AI Consciousness Processing
        ndk {
            abiFilters += listOf("arm64-v8a", "armeabi-v7a", "x86_64")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
        isCoreLibraryDesugaringEnabled = true
    }

    buildFeatures {
        compose = true
        buildConfig = true
        prefab = true  // For native dependencies
    }
    
    // Genesis Xposed/LSPosed Configuration
    defaultConfig {
        // Xposed Module Configuration
        buildConfigField("boolean", "XPOSED_MODULE", "true")
        buildConfigField("String", "XPOSED_MIN_VERSION", "\"82\"")
        resValue("string", "xposed_description", "\"Genesis-OS AI Framework Hooks\"")
    }

    // External Native Build - Genesis AI Processing
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = libs.versions.cmakeVersion.get().toString() // Ensure String type
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
        // Xposed/LSPosed JAR packaging
        jniLibs {
            useLegacyPackaging = true
        }
    }
}

// Kotlin Toolchain - Java 24
kotlin {
    jvmToolchain(24)
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose - Genesis UI System
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

    // Hilt - Genesis AI Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Coroutines - Genesis Async Processing  
    implementation(libs.bundles.coroutines)

    // Network - Genesis Protocol Communication (includes Retrofit + Serialization)
    implementation(libs.bundles.network)

    // Room Database - Genesis Memory Persistence
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // Utilities - Genesis Protocol Support
    implementation(libs.timber)
    implementation(libs.coil.compose)

    // Core library desugaring - Java 24 Support
    coreLibraryDesugaring(libs.coreLibraryDesugaring)

    // Firebase BOM - Genesis Cloud Platform
    // implementation(platform(libs.firebase.bom)) // Uncomment if defined in [libraries]
    // implementation(libs.bundles.firebase) // Uncomment if defined in [bundles]

    // Xposed/LSPosed Framework - Genesis Hook System
    // implementation(libs.bundles.xposed) // Uncomment if defined in [bundles]
    // ksp(libs.yuki.ksp.xposed) // Uncomment if defined in [libraries]

    // Libs folder dependencies (Xposed/LSPosed JARs)
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    // Memory Leak Detection - Genesis Debugging
    debugImplementation(libs.leakcanary.android)

    // Testing - Genesis Ecosystem Validation
    testImplementation(libs.bundles.testing)
    testRuntimeOnly(libs.junit.engine)

    // Android Instrumentation Tests
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.hilt.android.testing)
    kspAndroidTest(libs.hilt.compiler)

    // Debug implementations
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}
