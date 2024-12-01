import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.ksp)
    id("kotlin-kapt")
    id("maven-publish")
}
val githubProperties = Properties()
githubProperties.load(rootProject.file("github.properties").inputStream())

android {
    namespace = "com.rupak.analyticsSDK"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}
publishing {
    publications {
        create<MavenPublication>("gpr") {
            run {
                groupId = "com.github.rupak"
                version = "1.0"
                artifact("$buildDir/outputs/aar/analyticssdk-debug.aar")
            }
        }
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/rup4kt44/analyticsSdk") // Github Package
                credentials {
                    //Fetch these details from the properties file or from Environment variables
                    username = githubProperties["gpr.usr"] as String
                    password = githubProperties["gpr.key"] as String
                }
            }
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Gson
    implementation(libs.gson)

    //room
    implementation(libs.room)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    //unit testing
    testImplementation(libs.mockito.core)
}