// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
    id("com.google.firebase.crashlytics") version "2.9.9" apply false
    id("com.google.devtools.ksp") version "1.9.0-1.0.13" apply false
    kotlin("kapt") version "1.9.0" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false

}