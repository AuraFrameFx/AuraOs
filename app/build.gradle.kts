plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    // alias(libs.plugins.kotlin.compose) // Temporarily disabled
    alias(libs.plugins.google.services) // Re-enabled - version 4.4.3 confirmed to exist
    alias(libs.plugins.firebase.crashlytics) // Uncomment if defined in [plugins]
    alias(libs.plugins.firebase.perf) // Uncomment if defined in [plugins]
    alias(libs.plugins.spotless)
    alias(libs.plugins.dokka)
    alias(libs.plugins.openapi.generator) apply true
}

import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

// OpenAPI Generator codegen tasks (migrated from openapi-generator.gradle.kts)
val openapiSpecs = listOf(
    Triple("ai", "ai-api.yml", "dev.aurakai.auraframefx.api.ai"),
    Triple("customization", "customization-api.yml", "dev.aurakai.auraframefx.api.customization"),
    Triple("genesis", "genesis-api.yml", "dev.aurakai.auraframefx.api.genesis"),
    Triple("oracleDrive", "oracle-drive-api.yml", "dev.aurakai.auraframefx.api.oracledrive"),
    Triple("sandbox", "sandbox-api.yml", "dev.aurakai.auraframefx.api.sandbox"),
    Triple("system", "system-api.yml", "dev.aurakai.auraframefx.api.system")
)

openapiSpecs.forEach { (name, spec, pkg) ->
    tasks.register("generate${name.replaceFirstChar { it.uppercase() }}ApiClient", GenerateTask::class) {
        generatorName.set("kotlin")
        library.set("jvm-retrofit2")
        inputSpec.set("${rootDir}/api-spec/$spec")
        outputDir.set(layout.buildDirectory.dir("generated/openapi/$name").get().asFile.absolutePath)
        packageName.set(pkg)
        configFile.set("${rootDir}/openapi-generator-config.json")
    }
}

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = 35
    ndkVersion = "27.2.12479018"
    // Ensure String type

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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

    // Add generated sources to main source set
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/ai/src/main/kotlin"))
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/customization/src/main/kotlin"))
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/genesis/src/main/kotlin"))
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/oracleDrive/src/main/kotlin"))
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/sandbox/src/main/kotlin"))
    sourceSets["main"].java.srcDir(layout.buildDirectory.dir("generated/openapi/system/src/main/kotlin"))
    kotlinOptions {
        jvmTarget = "11"
    }
    buildToolsVersion = "35.0.0"
}

afterEvaluate {
    tasks.findByName("preBuild")?.dependsOn(
        "generateAiApiClient",
        "generateCustomizationApiClient",
        "generateGenesisApiClient",
        "generateOracleDriveApiClient",
        "generateSandboxApiClient",
        "generateSystemApiClient"
    )
}

// Kotlin Toolchain - Java 21
kotlin {
    jvmToolchain(11)
}

dependencies {
    // Core AndroidX
    implementation(libs.androidx.appcompat)
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
