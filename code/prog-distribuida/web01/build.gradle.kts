plugins {
    id("java")
    id("war")
}

group = "org.example"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // Source: https://mvnrepository.com/artifact/commons-collections/commons-collections
    implementation("commons-collections:commons-collections:3.2.2")
    // Source: https://mvnrepository.com/artifact/jakarta.ws.rs/jakarta.ws.rs-api
    compileOnly("jakarta.ws.rs:jakarta.ws.rs-api:4.0.0")
}

tasks.test {
    useJUnitPlatform()
}