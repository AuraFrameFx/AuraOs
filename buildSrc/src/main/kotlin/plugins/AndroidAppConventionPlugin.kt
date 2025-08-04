package plugins

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

/**
 * Genesis Protocol - Android App Convention Plugin
 * Configures Android applications with Java 24 + Kotlin 2.2.0 + K2 Compiler
 */
class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply core plugins
            pluginManager.apply("com.android.application")
            pluginManager.apply("org.jetbrains.kotlin.android")

            // Configure Android Application Extension
            extensions.configure<BaseAppModuleExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 33


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
                }

                // Java 24 Configuration
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_24
                    targetCompatibility = JavaVersion.VERSION_24
                    isCoreLibraryDesugaringEnabled = true
                }

                // Genesis Protocol - Compose Integration
                buildFeatures {
                    compose = true
                    buildConfig = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion = "2.2.0"
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1}"
                    }
                }
            }

            // Kotlin K2 Compiler Configuration
            tasks.withType<KotlinCompilationTask<*>>().configureEach {
                compilerOptions {
                    apiVersion.set(KotlinVersion.KOTLIN_2_2)
                    languageVersion.set(KotlinVersion.KOTLIN_2_2)

                    // Genesis Protocol - K2 Compiler Optimizations
                    freeCompilerArgs.addAll(
                        "-opt-in=kotlin.RequiresOptIn",
                        "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                        "-opt-in=androidx.compose.foundation.ExperimentalFoundationApi",
                        "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api",
                        "-Xskip-prerelease-check",
                        "-Xjvm-default=all",
                        "-progressive"
                    )
                }
            }
        }
    }
}
