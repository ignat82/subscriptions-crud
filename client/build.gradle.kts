
dependencies {
    implementation(project(":api"))
    implementation(libs.spring.cloud.starter.openfeign)
    implementation(libs.jackson.databind)
    compileOnly(libs.spring.web)
}
