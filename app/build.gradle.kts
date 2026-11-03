plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "org.opendeck.launcher"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.opendeck.launcher"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
        debug {
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    // Use AndroidX artifacts
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // AndroidX Core
    implementation("androidx.core:core-ktx:1.14.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0")
    implementation("androidx.activity:activity-ktx:1.7.2")
    implementation("androidx.annotation:annotation:1.7.0")

    // Room persistence
    implementation("androidx.room:room-ktx:2.5.0")
    kapt("androidx.room:room-compiler:2.5.0")
    implementation("androidx.room:room-rxjava2:2.5.0")

    // DataStore
    implementation("androidx.datastore:datastore:1.1.1")
    implementation("androidx.datastore:datastore-core:1.1.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // Kotlin coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Activity and Fragment
    implementation("androidx.activity:activity:1.7.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // ViewPager2 for workspace pages
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // Material Design
    implementation("com.google.android.material:material:1.12.0")

    // Lifecycle ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.0")

    // ConstraintLayout for flexible UIs
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    // Testing
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test:runner:1.6.1")
    androidTestImplementation("androidx.test:rules:1.6.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.6")
}