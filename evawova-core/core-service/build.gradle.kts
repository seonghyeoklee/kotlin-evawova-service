dependencies {
    implementation(project(":evawova-adapters:adapter-http"))
    implementation(project(":evawova-core:core-usecase"))
    implementation(project(":evawova-core:core-port"))
    implementation(project(":evawova-core:core-domain"))
    implementation(project(":evawova-commons"))

    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-tx")
}
