import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.SpringBootPluginExtension

val kafkaVersion by project
val jacksonVersion by project

plugins {
    val kotlinVersion = "1.1.61"
    val springDependencyManagement = "1.0.4.RELEASE"
    val springBootVersion = "1.5.9.RELEASE"
    val junitGradleVersion = "1.0.2"
    val dockerPluginVersion = "0.17.2"

    base
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion apply false
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion //apply false
    id("org.springframework.boot") version springBootVersion apply false
    id("io.spring.dependency-management") version springDependencyManagement apply false
    id("com.palantir.docker") version dockerPluginVersion apply false
}

subprojects {

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.jetbrains.kotlin.plugin.noarg")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
    }

    repositories {
        mavenCentral()
        mavenLocal()
        maven("https://repo.spring.io/milestone")
        maven("https://repo.spring.io/snapshot" )
    }

    dependencies {
        // kotlin
        compile("org.jetbrains.kotlin:kotlin-stdlib-jre8")
        compile("org.jetbrains.kotlin:kotlin-reflect")
        // Web
        compile("org.springframework.boot:spring-boot-starter-web")
        compile("com.fasterxml.jackson.module:jackson-module-kotlin")
        compile("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    }

    tasks {
        withType<KotlinCompile>().all {
            kotlinOptions {
                jvmTarget = "1.8"
                freeCompilerArgs = listOf("-Xjsr305=strict")
            }
        }
    }

    configure<SpringBootPluginExtension> {
        buildInfo()
    }

    noArg {
        annotation("org.sumo.klogs.NoArg")
    }
}
