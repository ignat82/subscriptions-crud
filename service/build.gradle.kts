dependencies {
    implementation(project(":api"))

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.data.jdbc)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.springdoc.openapi)
    implementation(libs.liquibase)
    implementation(libs.slf4j.api)

    runtimeOnly(libs.postgresql)

    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.spring.boot.starter.test)
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
}

plugins {
    java
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
    jacoco
}

configure<PublishingExtension> {
    publications {
        getByName<MavenPublication>("maven") {
            artifact(tasks.getByName("bootJar"))
        }
    }
}

tasks.register("testCoverageReport") {
    dependsOn("test", "jacocoTestReport")

    doLast {
        val jacocoReportFile = layout.buildDirectory.file("reports/jacoco/test/jacocoTestReport.xml").get().asFile
        if (!jacocoReportFile.exists()) {
            println("JaCoCo report file not found. Run tests first.")
            return@doLast
        }

        val doc = with(javax.xml.parsers.DocumentBuilderFactory.newInstance()) {
            setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
            newDocumentBuilder().parse(jacocoReportFile)
        }

        val countersAttributes = doc.getElementsByTagName("counter").let { nodeList ->
            (0 until nodeList.length).map { nodeList.item(it) }
        }.map { it.attributes }

        var branchTotal = 0; var branchMissed = 0
        var lineTotal = 0; var lineMissed = 0
        var complexityTotal = 0; var complexityMissed = 0

        countersAttributes.onEach { attributes ->
            val type = attributes.getNamedItem("type").nodeValue
            val missed = attributes.getNamedItem("missed").nodeValue.toInt()
            val covered = attributes.getNamedItem("covered").nodeValue.toInt()

            when (type) {
                "BRANCH" -> {
                    branchMissed = missed
                    branchTotal = missed + covered
                }
                "LINE" -> {
                    lineMissed = missed
                    lineTotal = missed + covered
                }
                "COMPLEXITY" -> {
                    complexityMissed = missed
                    complexityTotal = missed + covered
                }
            }
        }

        val branchPercentage = if (branchTotal > 0) (branchTotal - branchMissed) * 100 / branchTotal else 0
        val linePercentage = if (lineTotal > 0) (lineTotal - lineMissed) * 100 / lineTotal else 0
        val complexityPercentage = if (complexityTotal > 0) (complexityTotal - complexityMissed) * 100 / complexityTotal else 0

        println("Branches coverage percentage: ${branchPercentage}%")
        println("Lines coverage percentage: ${linePercentage}%")
        println("Complexity: ${complexityMissed} missed of ${complexityTotal}. Coverage percentage: ${complexityPercentage}%")
    }
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    finalizedBy(tasks.named("jacocoTestReport"))
    tasks.named("jacocoTestReport").get().finalizedBy(tasks.named("testCoverageReport"))
}