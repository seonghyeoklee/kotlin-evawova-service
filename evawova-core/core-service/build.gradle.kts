dependencies {
    implementation(project(":evawova-core:core-usecase"))
    implementation(project(":evawova-core:core-port"))
    implementation(project(":evawova-core:core-domain"))
    implementation(project(":evawova-commons"))

    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework:spring-context")

    implementation("io.jsonwebtoken:jjwt-api")
    implementation("io.jsonwebtoken:jjwt-impl")
    implementation("io.jsonwebtoken:jjwt-jackson")
}
