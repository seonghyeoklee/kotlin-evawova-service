pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            url = uri("https://maven.springframework.org/release")
        }
        maven {
            url = uri("https://maven.restlet.com")
        }
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "kotlin-evawova-service"

include("evawova-apps:app-admin-api")
include("evawova-apps:app-batch")

include("evawova-adapters:adapter-http")
include("evawova-adapters:adapter-persistence")
include("evawova-adapters:adapter-redis")

include("evawova-core:core-domain")
include("evawova-core:core-port")
include("evawova-core:core-service")
include("evawova-core:core-usecase")

include("evawova-commons")
