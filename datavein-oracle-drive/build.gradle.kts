@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("android-library-conventions")
    id("openapi-generation-conventions")id("spotless-conventions")
    id("detekt-conventions")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)

    // Genesis Protocol Convention Plugins
    id("OracleDriveConventionPlugin")
    id("DocumentationConventionPlugin")
    id("SecureCommunicationConventionPlugin")
}

android {
    namespace = "dev.aurakai.auraframefx.oracledrive"
    compileSdk = libs.versions.compileSdk.get().toInt()
    ndkVersion = "27.0.12077973"

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        // NDK Configuration for ROM Engineering
        ndk {
            abiFilters.addAll(listOf("arm64-v8a", "armeabi-v7a", "x86_64", "x86"))
        }

        externalNativeBuild {
            cmake {
                cppFlags("-std=c++20", "-frtti", "-fexceptions")
                arguments(
                    "-DANDROID_STL=c++_shared",
                    "-DCMAKE_VERBOSE_MAKEFILE=ON",
                    "-DCMAKE_BUILD_TYPE=Release",
                    "-DANDROID_ARM_NEON=TRUE"
                )
                abiFilters("arm64-v8a", "armeabi-v7a", "x86_64", "x86")
            }
        }

        // Build config fields for ROM development
        buildConfigField("boolean", "ENABLE_NATIVE_LOGGING", "true")
        buildConfigField("boolean", "ENABLE_ROM_ANALYSIS", "true")
        buildConfigField("String", "ORACLE_DRIVE_VERSION", "\"2.0.0\"")
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
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_24
        targetCompatibility = JavaVersion.VERSION_24
        isCoreLibraryDesugaringEnabled = true
    }

    kotlinOptions {
        jvmTarget = "24"
        freeCompilerArgs += listOf(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
        )
    }

    buildFeatures {
        compose = true
        buildConfig = true
        aidl = true
        viewBinding = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    // CMake configuration for native ROM processing
    externalNativeBuild {
        cmake {
            path("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core AndroidX
    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.lifecycle.viewmodel.compose)

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.activity.compose)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Coroutines & Flow
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Networking for API communication
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Room Database for ROM metadata storage
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    // DataStore for settings
    implementation(libs.datastore.preferences)

    // Material Design
    implementation(libs.material3)
    implementation(libs.material.icons.extended)

    // Firebase for backend communication
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.storage)
    implementation(libs.firebase.functions)

    // ROM Engineering Dependencies
    implementation("org.apache.commons:commons-compress:1.24.0")
    implementation("org.tukaani:xz:1.9")
    implementation("com.github.stephenc.jcip:jcip-annotations:1.0-1")

    // Core desugar for Java 8+ APIs
    coreLibraryDesugaring(libs.desugar.jdk.libs)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}

// Dokka configuration for comprehensive documentation
dokka {
    dokkaSourceSets {
        named("main") {
            displayName.set("Oracle Drive - ROM Engineering Module")
            includeNonPublic.set(false)
            skipEmptyPackages.set(true)

            perPackageOption {
                matchingRegex.set(".*\\.internal.*")
                suppress.set(true)
            }
        }
    }
}

// Configure OpenAPI generation for Oracle Drive API
tasks.named("generateOracleDriveApiClient") {
    doFirst {
        println("Generating Oracle Drive API client for ROM engineering capabilities...")
    }
}
