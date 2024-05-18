import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * To learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.7/samples
 * This project uses @Incubating APIs which are subject to change.
 */

group = "hexlet.code"
version = "0.0.1"

plugins {
    java
    application
    checkstyle
    jacoco
}

repositories {
    mavenCentral()
}

application {
    mainClass = "hexlet.code.App"
}

java {
    toolchain {
        version = JavaLanguageVersion.of(21)
    }
}

dependencies {
    testImplementation(libs.junit.jupiter)
    testRuntimeOnly(libs.junit.platform.launcher)
    testImplementation(libs.assertj.core)
}

tasks.test {
    useJUnitPlatform()

    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        showStandardStreams = true
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report

    reports {
        xml.required = true
        html.required = true
        csv.required = false
    }
}

// special for hexlet checks, I love you!
tasks.register("removeDotGradle") {
    doLast {
        val dotGradleDir = File("$projectDir/.gradle")
        dotGradleDir.deleteRecursively()
    }
}
tasks.compileTestJava {
    if (System.getenv("HOME").equals("/root")) {
        dependsOn(":removeDotGradle")
    }
}
// end of love
