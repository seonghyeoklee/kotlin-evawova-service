dependencies {
    implementation(project(":evawova-core:core-port"))
    implementation(project(":evawova-core:core-domain"))
    implementation(project(":evawova-commons"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework:spring-tx")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("com.mysql:mysql-connector-j")
    runtimeOnly(project(":evawova-core:core-service"))
}
