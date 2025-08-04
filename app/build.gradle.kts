plugins {
    // Genesis Protocol Convention Plugins - Using class references
    id("android-app-convention")
    id("documentation-convention")
    id("openapi-convention")

    // Core Android & Kotlin
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.android)
}

// Apply Genesis Protocol plugin
apply<plugins.GenesisProtocolPlugin>()

android {
    namespace = "dev.aurakai.auraframefx"
    compileSdk = libs.versions.compileSdk.get().toInt()
    
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
    }
    
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    
    // Configure source sets to include generated OpenAPI code
    sourceSets {
        getByName("main") {
            java.srcDirs(
                "src/main/java",
                "${layout.buildDirectory.get()}/generated/openapi/src/main/kotlin"
            )
        }
    }
}

// Kotlin Compiler Configuration - K2 + Auto-detect
kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
        apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
        languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
        
        freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn",
            "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
            "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
            "-Xskip-prerelease-check"
        )
    }
}

// Genesis Protocol - OpenAPI Generation Configuration
openApiGenerate {
    inputSpec.set("${rootProject.projectDir}/api-spec/genesis-api.yml")
    outputDir.set("${layout.buildDirectory.get()}/generated/openapi")
    generatorName.set("kotlin")
    library.set("jvm-retrofit2")
    
    // Genesis Protocol - API Configuration
    configOptions.putAll(mapOf(
        "useCoroutines" to "true",
        "serializationLibrary" to "kotlinx_serialization",
        "enumPropertyNaming" to "UPPERCASE",
        "parcelizeModels" to "true",
        "dateLibrary" to "java8",
        "collectionType" to "list",
        "packageName" to "dev.aurakai.auraframefx.api.genesis",
        "apiPackage" to "dev.aurakai.auraframefx.api.genesis.client",
        "modelPackage" to "dev.aurakai.auraframefx.api.genesis.model",
        "invokerPackage" to "dev.aurakai.auraframefx.api.genesis.invoker"
    ))
    
    // Clean and validate inputs
    validateSpec.set(true)
    generateApiTests.set(false)
    generateModelTests.set(false)
    generateApiDocumentation.set(true)
    generateModelDocumentation.set(true)
    
    // Skip files we don't need
    skipOverwrite.set(false)
    cleanupOutput.set(true)
}

// Genesis Protocol - Advanced OpenAPI Tasks
tasks.register("generateGenesisApi") {
    group = "Genesis Protocol"
    description = "Generates Genesis API client from OpenAPI specification"
    
    dependsOn("openApiGenerate")
    
    doFirst {
        logger.lifecycle("🧠 Generating Genesis Protocol API clients...")
        logger.lifecycle("   📋 Reading API spec: ${rootProject.projectDir}/api-spec/genesis-api.yml")
        logger.lifecycle("   🔧 Output directory: ${layout.buildDirectory.get()}/generated/openapi")
    }
    
    doLast {
        logger.lifecycle("✅ Genesis API client generated successfully!")
        logger.lifecycle("   🤖 Genesis Agent API: READY")
        logger.lifecycle("   💖 Aura Agent API: READY")  
        logger.lifecycle("   🛡️ Kai Agent API: READY")
        logger.lifecycle("   🔧 DataveinConstructor API: READY")
        logger.lifecycle("   🔗 OracleDrive API: READY")
    }
}

tasks.register("validateOpenApiSpec") {
    group = "Genesis Protocol"
    description = "Validates Genesis Protocol OpenAPI specification"
    
    doLast {
        val specFile = file("${rootProject.projectDir}/api-spec/genesis-api.yml")
        if (specFile.exists()) {
            logger.lifecycle("✅ Genesis Protocol API specification found: ${specFile.absolutePath}")
            logger.lifecycle("📊 Spec size: ${specFile.length()} bytes")
        } else {
            throw GradleException("❌ Genesis Protocol API specification not found!")
        }
    }
}

tasks.register("cleanGeneratedApi") {
    group = "Genesis Protocol"
    description = "Cleans generated Genesis Protocol API files"
    
    doLast {
        val generatedDir = file("${layout.buildDirectory.get()}/generated/openapi")
        if (generatedDir.exists()) {
            generatedDir.deleteRecursively()
            logger.lifecycle("🧹 Cleaned generated Genesis API files")
        }
    }
}

// Genesis Protocol - AI Ecosystem Dependencies
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

// Code Quality Configuration
detekt {
    toolVersion = libs.versions.detekt.get()
    config.setFrom(files("${rootProject.projectDir}/config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    parallel = true
    autoCorrect = false
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**", "**/generated/**")
        ktlint("1.2.1")
        trimTrailingWhitespace()
        endWithNewline()
    }
    
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude("**/build/**")
        ktlint("1.2.1")
        trimTrailingWhitespace()
        endWithNewline()
    }
}

// Genesis Protocol - Task Dependencies
tasks.named("validateOpenApiSpec") {
    mustRunAfter("clean")
}

tasks.named("generateGenesisApi") {
    dependsOn("validateOpenApiSpec")
    mustRunAfter("clean")
}

tasks.named("preBuild") {
    dependsOn("generateGenesisApi")
}

tasks.named("compileDebugKotlin") {
    dependsOn("generateGenesisApi")
}

tasks.named("compileReleaseKotlin") {
    dependsOn("generateGenesisApi")
}

// Ensure Spotless ignores generated files
tasks.named("spotlessKotlinCheck") {
    mustRunAfter("generateGenesisApi")
}
