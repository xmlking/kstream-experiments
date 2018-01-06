pluginManagement {
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://jcenter.bintray.com/") }
        maven { url = uri("http://repo.spring.io/plugins-release") }
    }

    resolutionStrategy {
        eachPlugin {
//            if (arrayOf("propdeps","propdeps-maven","propdeps-idea","propdeps-eclipse").contains(requested.id.id)) {
//                useModule("io.spring.gradle:propdeps-plugin:${requested.version}")
//            }
            if (requested.id.id == "org.junit.platform.gradle.plugin") {
                useModule("org.junit.platform:junit-platform-gradle-plugin:${requested.version}")
            }
        }
    }
}
include( "shared", "word-count", "product", "raw-word-count")
