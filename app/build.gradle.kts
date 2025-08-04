import plugins.AndroidAppConventionPlugin
import plugins.OpenApiConventionPlugin
import plugins.DocumentationConventionPlugin

plugins {
    id("android-app-convention")
    id("openapi-convention")
    id("documentation-convention")
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
}
