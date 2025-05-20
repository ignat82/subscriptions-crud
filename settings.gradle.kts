rootProject.name = "subscriptions-crud"

include("api")
include("client")
include("service")

pluginManagement {

    repositories {
        mavenCentral()
        gradlePluginPortal()
    }

    plugins {
        //kotlin("jvm") version "2.0.20"
        id("org.springframework.boot") version "3.2.0"
        id("io.spring.dependency-management") version "1.1.4"
    }
}
