plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
    alias(libs.plugins.dagger.hilt.android)
}

android {
    namespace = "com.example.pickleverse"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.pickleverse"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    // Activity
    implementation(libs.androidx.activity.ktx)

    // Fragment
    implementation(libs.androidx.fragment.ktx)

    // Hilt
    implementation(libs.dagger.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)

    // Recyclerview
    implementation(libs.androidx.recyclerview)
    // For control over item selection of both touch and mouse driven selection
    implementation(libs.androidx.recyclerview.selection)

    // Glide
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // Lottie
    implementation("com.airbnb.android:lottie:3.4.0")
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = false
}