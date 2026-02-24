plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.petlink"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.petlink"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"

        // 通过 Gradle 属性注入高德 Key：
        // 方式1：~/.gradle/gradle.properties 里加 AMAP_API_KEY=xxx
        // 方式2：项目根目录 gradle.properties 里加 AMAP_API_KEY=xxx
        val amapKey: String? = (project.findProperty("AMAP_API_KEY") as String?)
        manifestPlaceholders["AMAP_API_KEY"] = amapKey ?: "REPLACE_ME"
    }

    buildFeatures { compose = true }
    composeOptions { kotlinCompilerExtensionVersion = "1.5.8" }
    kotlinOptions { jvmTarget = "17" }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.material3:material3:1.2.1")

    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.3")

    // 高德地图 SDK（MavenCentral）
    implementation("com.amap.api:3dmap:10.0.600")
}
