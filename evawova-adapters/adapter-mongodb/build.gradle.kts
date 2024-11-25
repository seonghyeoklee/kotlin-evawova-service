dependencies {
    implementation(project(":evawova-core:core-port"))
    implementation(project(":evawova-core:core-domain"))
    implementation(project(":evawova-commons"))

    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework:spring-tx")

    runtimeOnly(project(":evawova-core:core-service"))
}