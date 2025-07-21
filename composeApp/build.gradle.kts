import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.cocoapods)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    targets.configureEach {
        compilations.configureEach {
            compileTaskProvider.get().compilerOptions {
                freeCompilerArgs.add("-Xexpect-actual-classes")
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Mediapiper"
            isStatic = true
        }
    }

    cocoapods {
        name = "Mediapiper"
        summary = "Some description for a Kotlin/Native module"
        homepage = "Link to a Kotlin/Native module homepage"
        version = "1.0.0"
        ios.deploymentTarget = "18"
        podfile = project.file("../iosApp/Podfile")

        // Optional properties
        // Configure the Pod name here instead of changing the Gradle project name
        // name = "MyCocoaPod"

//        framework {
//            // Required properties
//            // Framework name configuration. Use this property instead of deprecated 'frameworkName'
//            baseName = "Mediapiper"
//
//            // Optional properties
//            // Specify the framework linking type. It's dynamic by default.
//            isStatic = true
//            // Dependency export
//            // Uncomment and specify another project module if you have one:
//            // export(project(":<your other KMP module>"))
////            transitiveExport = false // This is default.
//        }

        // Maps custom Xcode configuration to NativeBuildType
//        xcodeConfigurationToNativeBuildType["CUSTOM_DEBUG"] = NativeBuildType.DEBUG
//        xcodeConfigurationToNativeBuildType["CUSTOM_RELEASE"] = NativeBuildType.RELEASE

        pod("MediaPipeTasksGenAIC") {
            version = "0.10.24"
            extraOpts += listOf("-compiler-option", "-fmodules")
            linkOnly = true
        }

        pod("MediaPipeTasksGenAI") {
            version = "0.10.24"
            extraOpts += listOf("-compiler-option", "-fmodules")
            linkOnly = true
        }

//        pod("GoogleSignIn") {
//            extraOpts += listOf("-compiler-option", "-fmodules")
//        }
//        pod("Firebase") {
//            extraOpts += listOf("-compiler-option", "-fmodules")
//            linkOnly = true
//        }
//        pod("FirebaseCore") {
//            extraOpts += listOf("-compiler-option", "-fmodules")
//            linkOnly = true
//        }
//        pod("FirebaseAuth") {
//            extraOpts += listOf("-compiler-option", "-fmodules")
//            linkOnly = true
//        }
//        pod("FirebaseFirestore") {
//            extraOpts += listOf("-compiler-option", "-fmodules")
//            linkOnly = true
//        }
    }

    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            // Koin
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.compose)

            implementation(libs.mediapipe.genai.android)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.androidx.navigation.compose)

            // Koin
            implementation(project.dependencies.platform(libs.koin.bom))
            implementation(libs.koin.core)
            api(libs.koin.annotations)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "com.gdgedinburgh.gemmaworkshop"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.gdgedinburgh.gemmaworkshop"
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
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    ksp(libs.koin.ksp.compiler)
    debugImplementation(compose.uiTooling)

}

