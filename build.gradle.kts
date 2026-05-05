// fichier de config Gradle
// Syntaxe utilisé : Kotlin DSL

plugins {
    // https://docs.gradle.org/current/userguide/java_plugin.html
    java
    // https://docs.gradle.org/current/userguide/application_plugin.html
    application
    // https://plugins.gradle.org/plugin/org.openjfx.javafxplugin
    id("org.openjfx.javafxplugin") version "0.1.0"
    id("org.beryx.jlink") version "3.0.1"
}

// Configuration de la version de Java (Optionnel mais recommandé)
java {
    // https://docs.gradle.org/current/userguide/java_plugin.html#sec:java-extension
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

application {
    mainClass = "cinema.app.MainApplication" // Classe principale
    mainModule = "cinema.app"
    // Arguments JVM par défaut pour JavaFX 23+ (voir section 3)
    applicationDefaultJvmArgs = listOf("--enable-native-access=javafx.graphics,javafx.controls,javafx.fxml")
}

javafx {
    // https://github.com/openjfx/javafx-gradle-plugin?tab=readme-ov-file#3-specify-javafx-version
    version = "21.0.4"
    // https://github.com/openjfx/javafx-gradle-plugin?tab=readme-ov-file#2-specify-javafx-modules
    modules("javafx.controls", "javafx.fxml")
}

group = "edu.ort.lyon"
version = "1.0.0"

// https://docs.gradle.org/current/userguide/declaring_repositories_basics.html#header
repositories {
    mavenCentral()
}

// https://docs.gradle.org/current/userguide/declaring_dependencies_basics.html
dependencies {
    // tests : https://docs.gradle.org/current/userguide/declaring_dependencies_basics.html#sec:testkit-dependencies
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // BDD
    implementation("org.postgresql:postgresql:42.7.4")
    implementation ("org.mindrot:jbcrypt:0.4")
}


tasks.test {
    useJUnitPlatform()
}


jlink {
    launcher {
        name = "CineForAll"
    }

    jpackage {
        imageName = "CineForAll"
        skipInstaller = true
    }

    forceMerge("jbcrypt")
}

tasks.withType<JavaCompile> {
    options.release.set(21)
    options.compilerArgs.add("--add-reads")
    options.compilerArgs.add("cinema.app=ALL-UNNAMED")
}

configurations {
    implementation {
        isCanBeResolved = true
    }
}