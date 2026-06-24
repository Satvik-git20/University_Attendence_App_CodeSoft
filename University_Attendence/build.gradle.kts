// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        // For KGP
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:2.1.0")
        // For KSP
        classpath("com.google.devtools.ksp:symbol-processing-gradle-plugin:2.1.0-1.0.29")
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
}
