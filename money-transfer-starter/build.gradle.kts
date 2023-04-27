plugins {
    id("org.springframework.boot") version "3.0.5"
    id("io.spring.dependency-management") version "1.1.0"
    id("maven-publish")
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.spring") version "1.7.22"
}

group = "ru.eremin.mt"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
    mavenLocal()
}

publishing {
    repositories {
        mavenLocal()
    }

    publications {
        create<MavenPublication>("mavenJar") {
            from(components["java"])
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    api("org.springframework.boot:spring-boot-starter-actuator")
    api("ru.eremin.mt:common:0.0.1")
}
