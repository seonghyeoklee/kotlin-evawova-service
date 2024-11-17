import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-server")
}

val appMainClassName = "com.evawova.EurekaServiceApplicationKt"
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
                "evawova-app-eureka-service:latest",
                ".",
            )
        }
    }
}
