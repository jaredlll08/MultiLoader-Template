plugins {
    kotlin("jvm")
    `maven-publish`
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val minecraftVersion: String by project
val commonRunsEnabled: String by project
val commonClientRunName: String? by project
val commonServerRunName: String? by project
val modName: String by project
val modId: String by project

val baseArchiveName = "${modName}-common-${minecraftVersion}"

base {
    archivesName.set(baseArchiveName)
}

minecraft {
    version(minecraftVersion)
    runs {
        if (commonRunsEnabled == "true") {
            client(commonClientRunName ?: "vanilla_client") {
                workingDirectory(project.file("run"))
            }
            server(commonServerRunName ?: "vanilla_server") {
                workingDirectory(project.file("run"))
            }
        }
    }
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.5")
}

tasks.processResources {

    val buildProps = project.properties

    filesMatching("pack.mcmeta") {

        expand(buildProps)
    }
}
publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = baseArchiveName
            from(components["java"])
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}