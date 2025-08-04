plugins {
    id("com.android.application") version "8.12.0" apply false
    id("com.android.library") version "8.12.0" apply false
    id("org.jetbrains.kotlin.android") version "2.2.0" apply false
    id("org.jetbrains.kotlin.jvm") version "2.2.0" apply false
    id("com.google.dagger.hilt.android") version "2.57" apply false
    id("com.google.devtools.ksp") version "2.2.0-2.0.2" apply false
}


allprojects {
    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(24))
            vendor.set(JvmVendorSpec.ADOPTIUM)
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}