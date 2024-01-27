
val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "1.9.22"
    id("io.ktor.plugin") version "2.3.7"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}

group = "asia.raywel51"
version = "0.0.2"

application {
    mainClass.set("asia.raywel51.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

tasks.jar {
    manifest {
        attributes("Main-Class" to "asia.raywel51.ApplicationKt")
    }
}


dependencies {
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation ("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-auto-head-response-jvm")
    implementation("io.ktor:ktor-server-cio-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation ("io.ktor:ktor-server-call-logging:$ktor_version")
    implementation("io.ktor:ktor-server-html-builder:$ktor_version")

    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.11.0")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

    implementation ("com.google.zxing:core:3.4.1")
    implementation ("com.google.zxing:javase:3.4.1")

    implementation("net.sf.jasperreports:jasperreports:6.17.0")
    implementation("com.lowagie:itext:2.1.7")
    implementation ("com.github.librepdf:openpdf:1.3.30")
}
