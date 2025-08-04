// This precompiled script plugin has been DISABLED
// It was causing compilation conflicts similar to android-application-conventions.gradle.kts
// 
// Unresolved reference 'android' errors occur because precompiled script plugins
// need explicit extension configuration that class-based plugins handle automatically.
//
// If you need Android library conventions, create a class-based plugin like:
// buildSrc/src/main/kotlin/plugins/AndroidLibraryConventionPlugin.kt
//
// This file is intentionally left empty to prevent build errors.
