plugins {
    // Android plugins - NO VERSION NUMBERS (from version catalog)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    alias(libs.plugins.spotless) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.dokka) apply false
    
    // Genesis Protocol Integration
    id("genesis.protocol")
}

// Configure Java toolchain for all subprojects - AUTO DETECT
subprojects {
    // Apply Java toolchain configuration
    plugins.withType<JavaBasePlugin> {
        configure<JavaPluginExtension> {
            toolchain {
                languageVersion.set(JavaLanguageVersion.of(24))
                vendor.set(JvmVendorSpec.ADOPTIUM)
            }
        }
    }
    
    // Configure Kotlin compiler - K2 COMPILER
    plugins.withId("org.jetbrains.kotlin.android") {
        configure<org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension> {
            compilerOptions {
                jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_24)
                apiVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
                languageVersion.set(org.jetbrains.kotlin.gradle.dsl.KotlinVersion.KOTLIN_2_2)
                
                // K2 Compiler optimization flags
                freeCompilerArgs.addAll(
                    "-Xskip-prerelease-check",
                    "-opt-in=kotlin.RequiresOptIn",
                    "-opt-in=kotlin.ExperimentalStdlibApi",
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    "-Xjvm-default=all",
                    "-progressive"
                )
            }
        }
    }
    
    // Configure Android applications
    plugins.withId("com.android.application") {
        configure<com.android.build.gradle.AppExtension> {
            compileSdkVersion(libs.versions.compileSdk.get().toInt())
            
            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()
                targetSdk = libs.versions.targetSdk.get().toInt()
            }
            
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_24
                targetCompatibility = JavaVersion.VERSION_24
                isCoreLibraryDesugaringEnabled = true
            }
            
            buildFeatures {
                compose = true
            }
            
            composeOptions {
                kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
            }
        }
    }
    
    // Configure Android libraries
    plugins.withId("com.android.library") {
        configure<com.android.build.gradle.LibraryExtension> {
            compileSdkVersion(libs.versions.compileSdk.get().toInt())
            
            defaultConfig {
                minSdk = libs.versions.minSdk.get().toInt()
            }
            
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_24
                targetCompatibility = JavaVersion.VERSION_24
                isCoreLibraryDesugaringEnabled = true
            }
            
            buildFeatures {
                compose = true
            }
            
            composeOptions {
                kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
            }
        }
    }
}

// Clean task
tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}
dependencies {
    implementation(kotlin("stdlib"))
}
repositories {
    mavenCentral()
}