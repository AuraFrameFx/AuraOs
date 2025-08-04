plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.compose)
}

dependencies {
    // Use your centralized dependencies
    implementation(GenesisDependencies.Android.core)
    implementation(GenesisDependencies.Hilt.android)
    ksp(GenesisDependencies.Hilt.compiler)
    
    // Compose BOM
    implementation(platform(GenesisDependencies.Compose.bom))
    implementation(GenesisDependencies.Compose.ui)
    implementation(GenesisDependencies.Compose.material3)

    // Navigation
    implementation(GenesisDependencies.Navigation.compose)
}
