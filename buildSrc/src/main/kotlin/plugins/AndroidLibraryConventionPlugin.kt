import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

/**
 * Genesis Protocol - Android Library Convention Plugin
 * Configures Android libraries with Java 24 + Kotlin 2.2.0 + K2 Compiler
 * Updated for modern Android Gradle Plugin APIs
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            // Apply core plugins
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.android")

            // Configure Android Library Extension
            extensions.configure<LibraryExtension> {
                compileSdk = 36

                defaultConfig {
                    minSdk = 33
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

                // Java 24 Configuration
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_24
                    targetCompatibility = JavaVersion.VERSION_24
                    isCoreLibraryDesugaringEnabled = true
                }

                // Modern Build Features Configuration
                buildFeatures {
                    compose = true
                    buildConfig = true
                    aidl = false
                    renderScript = false
                    resValues = false
                    shaders = false
                }

                // Modern Compose Configuration
                composeOptions {
                    kotlinCompilerExtensionVersion = "2.2.0"
                }

                // Modern Packaging Configuration
                packaging {
                    resources {
                        excludes.addAll(
                            listOf(
                                "/META-INF/{AL2.0,LGPL2.1}",
                                "/META-INF/versions/9/previous-compilation-data.bin"
                            )
                        )
                    }
                }

                // Modern Lint Configuration
                lint {
                    targetSdk = 36
                    checkReleaseBuilds = false
                    abortOnError = false
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
