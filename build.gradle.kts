plugins {
    id("io.spring.dependency-management") version "1.1.2"
    kotlin("jvm") version "1.9.0"
    application
}

group = "com.ijsy.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")
    implementation("io.projectreactor:reactor-core:3.5.8")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(11)
}

application {
    mainClass.set("MainKt")
}