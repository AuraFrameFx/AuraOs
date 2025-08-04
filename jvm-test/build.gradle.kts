plugins {
    kotlin("jvm") version "2.2.0"
    alias(libs.plugins.dokka)
}

kotlin {
    jvmToolchain(24)
}

dependencies {
    implementation(libs.yuki) // If defined in libs.versions.toml
    implementation(libs.lsposed) // If defined in libs.versions.toml
    testImplementation(kotlin("test"))
}
