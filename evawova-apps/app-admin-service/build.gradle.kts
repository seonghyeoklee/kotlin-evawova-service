import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation(project(":evawova-core:core-domain"))
    implementation(project(":evawova-core:core-usecase"))
    implementation(project(":evawova-commons"))

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-aop")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("io.micrometer:micrometer-registry-prometheus")

    implementation(project(":evawova-adapters:adapter-http"))
//    implementation(project(":evawova-adapters:adapter-kafka"))
//    implementation(project(":evawova-adapters:adapter-mongodb"))
    implementation(project(":evawova-adapters:adapter-persistence"))
//    implementation(project(":evawova-adapters:adapter-redis"))
}

val appMainClassName = "com.evawova.AdminServiceApplicationKt"
tasks.getByName<BootJar>("bootJar") {
    mainClass.set(appMainClassName)
    archiveClassifier.set("boot")
}

tasks.register("dockerBuild") {
    group = "docker"
    description = "Builds a Docker image for the application."

    dependsOn(
        subprojects.flatMap { project ->
            listOf("${project.path}:clean", "${project.path}:build")
        },
    )

    dependsOn(tasks.getByName("bootJar"))

    doLast {
        exec {
            commandLine(
                "docker",
                "build",
                "-t",
                "evawova-app-admin-service:latest",
                ".",
            )
        }
        exec {
            commandLine(
                "docker",
                "image",
                "prune",
                "-f",
            )
        }
    }
}
