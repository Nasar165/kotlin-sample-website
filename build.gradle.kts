import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    application
    id("org.jetbrains.kotlin.plugin.serialization") version "1.6.20-M1"
}

group = "me.mcs"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core:1.6.7")
    implementation("io.ktor:ktor-server-netty:1.6.7")
    implementation("io.ktor:ktor-serialization:1.6.7")
    implementation("ch.qos.logback:logback-classic:1.2.5")
    implementation("org.litote.kmongo:kmongo-serialization:4.5.0")
    implementation("org.litote.kmongo:kmongo-id-serialization:4.5.0")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "16"
}

application {
    mainClass.set("MainKt")
}