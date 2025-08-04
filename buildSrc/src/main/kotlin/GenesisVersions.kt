object GenesisVersions {
    // Build Tools
    const val agp = "8.12.0"
    const val kotlin = "2.2.0"
    const val ksp = "2.2.0-2.0.2"
    const val gradle = "9.0.0"

    // Java/JVM
    const val jvmTarget = "21"           // Bytecode target
    const val javaToolchain = "24"       // Toolchain version

    // Android
    const val compileSdk = 36
    const val targetSdk = 36
    const val minSdk = 33
    const val ndk = "26.2.11394342"      // NDK VERSION - WAS MISSING

    // Compose
    const val compose = "1.9.0"
    const val composeBom = "2025.07.00"
    const val composeCompiler = "1.6.0"

    // Core Libraries
    const val hilt = "2.57"
    const val retrofit = "3.0.0"
    const val okhttp = "5.1.0"
    const val room = "2.7.2"
    const val lifecycle = "2.9.2"
    const val coroutines = "1.10.2"

    // Navigation - WAS MISSING
    const val navigation = "2.9.3"

    // Code Quality
    const val detekt = "1.23.8"
    const val spotless = "7.2.1"
    const val dokka = "2.0.0"

    // OpenAPI
    const val openApiGenerator = "7.14.0"

    // Testing
    const val junit = "5.13.4"
    const val mockk = "1.14.5"
    const val turbine = "1.2.1"
}
