import com.linecorp.support.project.multi.recipe.configureByLabels

plugins {
    kotlin("jvm") version "1.9.25"
    kotlin("plugin.spring") version "1.9.25"
    kotlin("plugin.jpa") version "1.9.25"
    kotlin("kapt") version "1.8.22"
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.linecorp.build-recipe-plugin") version "1.1.1"
    id("com.palantir.docker") version "0.36.0"
    id("com.google.cloud.tools.jib") version "3.4.0"
}

allprojects {
    group = "com.evawova"

    repositories {
        mavenCentral()
        maven { url = uri("https://maven.restlet.com") }
        maven { url = uri("https://jitpack.io") }
    }
}

subprojects {
    apply(plugin = "idea")
}

configureByLabels("kotlin") {
    apply(plugin = "java")
    apply(plugin = "kotlin")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "kotlin-spring")
    apply(plugin = "kotlin-jpa")
    apply(plugin = "kotlin-kapt")
    apply(plugin = "com.palantir.docker")
    apply(plugin = "com.google.cloud.tools.jib")

    dependencies {
        implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
        implementation(rootProject.libs.auth0.jwt)
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.1.0")
        implementation("org.springframework.boot:spring-boot-starter-websocket")
        implementation("org.springframework:spring-websocket:6.0.4")
        implementation("com.squareup.okhttp3:okhttp:4.11.0")
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
        testImplementation("io.kotest:kotest-assertions-core:5.6.2")
        testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
    }

    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom("org.springframework.boot:spring-boot-dependencies:3.3.5")
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.3")
            mavenBom("com.google.guava:guava-bom:31.1-jre")
        }
    }

    configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "17"
        }
    }

    kotlin {
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict")
        }

        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
            vendor.set(JvmVendorSpec.AMAZON)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    allOpen {
        annotation("javax.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
        annotation("javax.persistence.Embeddable")
    }

    noArg {
        annotation("javax.persistence.Entity")
        annotation("jakarta.persistence.Entity")
        annotation("javax.persistence.MappedSuperclass")
    }
}

configureByLabels("boot") {
    apply(plugin = "org.springframework.boot")

    tasks.getByName<Jar>("jar") {
        enabled = false
    }

    tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
        enabled = true
        archiveClassifier.set("boot")
    }
}

configureByLabels("library") {
    apply(plugin = "java-library")

    tasks.getByName<Jar>("jar") {
        enabled = true
    }

    tasks.getByName<Jar>("bootJar") {
        enabled = false
    }
}

configureByLabels("querydsl") {
    the<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension>().apply {
        imports {
            mavenBom("com.querydsl:querydsl-bom:5.0.0")
        }

        dependencies {
            dependency("com.querydsl:querydsl-core:5.0.0")
            dependency("com.querydsl:querydsl-jpa:5.0.0")
            dependency("com.querydsl:querydsl-apt:5.0.0")
        }
    }

    dependencies {
        implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
        kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
    }
}
