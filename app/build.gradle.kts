plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "ru.kpfu.homeworks"
    compileSdk = 33

    defaultConfig {
        applicationId = "ru.kpfu.homeworks"
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        viewBinding = true
    }
}

dependencies {

    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    val coreCtxVersion = "1.9.0"
    implementation("androidx.core:core-ktx:$coreCtxVersion")

    val appcompatVersion = "1.6.1"
    implementation("androidx.appcompat:appcompat:$appcompatVersion")

    val androidMaterialVersion = "1.9.0"
    implementation("com.google.android.material:material:$androidMaterialVersion")

    val viewBindingDelegateVersion = "1.5.9"
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:$viewBindingDelegateVersion")
}