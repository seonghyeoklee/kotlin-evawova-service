import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":evawova-core:core-usecase"))
    implementation(project(":evawova-commons"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    runtimeOnly(project(":evawova-adapters:adapter-http"))
    runtimeOnly(project(":evawova-adapters:adapter-persistence"))
    runtimeOnly(project(":evawova-adapters:adapter-redis"))

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    testImplementation("io.kotest:kotest-runner-junit5:5.6.2")
    testImplementation("io.kotest:kotest-assertions-core:5.6.2")
    testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.3")
}

val appMainClassName = "com.evawova.EvawovaApiApplicationKt"
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(appMainClassName)
    archiveClassifier.set("boot")
}

tasks.register("dockerBuild") {
    group = "docker"
    description = "Builds a Docker image for the application."

    dependsOn(tasks.getByName("bootJar"))

    doLast {
        exec {
            commandLine(
                "docker",
                "build",
                "-t",
                "evawova-app-api:latest",
                ".",
            )
        }
    }
}
