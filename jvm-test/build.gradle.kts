plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    jvmToolchain(24)
}

dependencies {
    implementation(libs.yuki) // If defined in libs.versions.toml
    implementation(libs.lsposed) // If defined in libs.versions.toml
    testImplementation(kotlin("test"))
}
