plugins {
    `java-library`
}

dependencies {
    api(project(":api"))

    implementation(libs.spring.cloud.starter.openfeign)
    implementation(libs.spring.boot.autoconfigure)

    annotationProcessor(libs.spring.boot.configuration.processor)
}
