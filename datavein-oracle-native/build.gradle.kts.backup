plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dokka)
    alias(libs.plugins.spotless)
}

android {
    namespace = "dev.aurakai.auraframefx.oraclenative"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
        isCoreLibraryDesugaringEnabled = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources.excludes += setOf(
            "/META-INF/{AL2.0,LGPL2.1}",
            "/META-INF/AL2.0",
            "/META-INF/LGPL2.1"
        )
    }
    
    sourceSets {
        getByName("main") {
            kotlin.srcDir("build/generated/openapi/src/main/kotlin")
        }
    }
}

kotlin {
    jvmToolchain(21)

    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        freeCompilerArgs.addAll(
            "-Xuse-k2",
            "-opt-in=kotlin.RequiresOptIn",
            "-Xjvm-default=all"
        )
    }
}

dependencies {
    // Project modules
    implementation(project(":core-module"))
    // Note: datavein-oracle-drive was removed from settings.gradle.kts

    // Core AndroidX
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose - Genesis UI System
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.navigation.compose)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    
    // Coroutines - Genesis Async Processing  
    implementation(libs.bundles.coroutines)
    
    // Kotlin reflection for KSP
    implementation("org.jetbrains.kotlin:kotlin-reflect:${libs.versions.kotlin.get()}")
    
    // OpenAPI Generated Code Dependencies
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.okhttp3.logging.interceptor)
    implementation(libs.kotlinx.serialization.json)

    // Core library desugaring
    coreLibraryDesugaring(libs.coreLibraryDesugaring)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // System interaction and documentation (using local JAR files)
    implementation(files("${project.rootDir}/Libs/api-82.jar"))
    implementation(files("${project.rootDir}/Libs/api-82-sources.jar"))
}
