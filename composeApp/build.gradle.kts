import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.container.composeContainer
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    kotlin("plugin.serialization") version "1.8.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        val desktopMain by getting

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("io.ktor:ktor-client-android:2.0.0")
            implementation("androidx.activity:activity-compose:1.6.1") // For ActivityResultContracts
            implementation("io.coil-kt:coil-compose:2.2.2")
            implementation("io.ktor:ktor-client-core:2.3.0") // Ktor for networking
            implementation("io.ktor:ktor-client-content-negotiation:2.3.0")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.0")
            implementation("com.google.code.gson:gson:2.8.9")
            implementation(libs.core.splashscreen)
            implementation ("com.russhwolf:multiplatform-settings:1.0.0")
            implementation ("com.russhwolf:multiplatform-settings-no-arg:1.0.0" )
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)
//            implementation("io.coil-kt:coil-compose:2.7.0")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(compose.material3)
            implementation("org.jetbrains.androidx.navigation:navigation-compose:2.7.0-alpha07")
            implementation("io.ktor:ktor-client-core:2.0.0")
            implementation("io.ktor:ktor-client-json:2.0.0")
            implementation("io.ktor:ktor-client-serialization:2.0.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
            implementation("com.google.code.gson:gson:2.8.9")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:your-version")

            implementation ("com.russhwolf:multiplatform-settings:1.0.0")
            implementation ("com.russhwolf:multiplatform-settings-no-arg:1.0.0" )
            implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")

            implementation("com.google.api-client:google-api-client:2.2.0")
            implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
            implementation("com.google.apis:google-api-services-drive:v3-rev20230815-2.0.0")
            implementation("com.google.api-client:google-api-client-gson:2.2.0")
            api(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.navigation.compose)
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.6.0")
            implementation("io.ktor:ktor-utils:2.3.4")
//            implementation("org.jetbrains.compose.resources:resources:1.0.0")
            
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

        iosMain {
            dependencies {
                implementation("io.ktor:ktor-client-ios:2.0.0")
                implementation("com.google.code.gson:gson:2.8.9")
            }
        }
        jsMain {
            dependencies {
                implementation("io.ktor:ktor-client-js:2.0.0")
                implementation("com.google.code.gson:gson:2.8.9")
            }
        }
    }
}

android {
    namespace = "org.example.anandsevakmp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.example.anandsevakmp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }

    }
    tasks.withType<Jar> {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/NOTICE")  // Optional: exclude additional files if needed
        exclude("META-INF/LICENSE") // Optional: exclude additional files if needed
    }
    
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}
dependencies {
    implementation(libs.gson)
    implementation(libs.play.services.cast.framework)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.foundation.android)
}

//dependencies {
//    implementation(libs.androidx.compiler)
//}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.anandsevakmp"
            packageVersion = "1.0.0"
        }
    }
}
