import org.gradle.api.tasks.bundling.Jar
import org.springframework.boot.gradle.repackage.RepackageTask

// Prevents common subproject dependencies from being included in the common jar itself.
// Without this, each subproject that included common would include each shared dependency twice.
tasks {
    withType<RepackageTask>() {
        enabled = false
    }
    withType<Jar> {
        enabled = true
    }
}
