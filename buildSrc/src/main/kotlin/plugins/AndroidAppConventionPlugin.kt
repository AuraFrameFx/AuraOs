import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension

class AndroidAppConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        with(project) {
            pluginManager.apply {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("com.google.devtools.ksp")
                apply("com.google.dagger.hilt.android")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            configure<BaseAppModuleExtension> {
                compileSdk = GenesisVersions.compileSdk

                // NDK CONFIGURATION - WAS MISSING
                ndkVersion = GenesisVersions.ndk

                defaultConfig {
                    minSdk = GenesisVersions.minSdk
                    targetSdk = GenesisVersions.targetSdk

                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    vectorDrawables.useSupportLibrary = true

                    // NDK CONFIGURATION - WAS MISSING
                    ndk {
                        abiFilters.addAll(listOf("arm64-v8a", "armeabi-v7a", "x86", "x86_64"))
                    }

                    // EXTERNAL NATIVE BUILD - WAS MISSING
                    externalNativeBuild {
                        cmake {
                            cppFlags.addAll(listOf("-std=c++17", "-frtti", "-fexceptions"))
                            arguments.addAll(listOf(
                                "-DANDROID_STL=c++_shared",
                                "-DANDROID_PLATFORM=android-${GenesisVersions.minSdk}"
                            ))
                        }
                    }
                }

                // EXTERNAL NATIVE BUILD CONFIGURATION - WAS MISSING
                externalNativeBuild {
                    cmake {
                        path = file("src/main/cpp/CMakeLists.txt")
                        version = "3.22.1"
                    }
                }

                buildFeatures {
                    compose = true
                    buildConfig = true
                }

                packaging {
                    resources {
                        excludes.addAll(listOf(
                            "/META-INF/{AL2.0,LGPL2.1}",
                            "META-INF/LICENSE*",
                            "META-INF/NOTICE*",
                            "DebugProbesKt.bin"
                        ))
                    }

                    // NDK PACKAGING - WAS MISSING
                    jniLibs {
                        useLegacyPackaging = false
                    }
                }

                testOptions {
                    unitTests.isIncludeAndroidResources = true
                    unitTests.isReturnDefaultValues = true
                }
            }

            // Configure Kotlin compilation
            kotlin {
                jvmToolchain(GenesisVersions.javaToolchain.toInt())
                compilerOptions {
                    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.fromTarget(GenesisVersions.jvmTarget))
                    freeCompilerArgs.addAll(
                        "-Xuse-k2",
                        "-Xjvm-default=all"
                    )
                }
            }
        }
    }
}
