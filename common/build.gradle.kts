plugins {
    idea
    java
    `maven-publish`
    id ("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val mod_id: String by project
val mod_name: String by project
val minecraft_version: String by project

base {
    archivesName = "${mod_name}-common-${minecraft_version}"
}

minecraft {
    version(minecraft_version)
    if (file("src/main/resources/${mod_id}.accesswidener").exists())
        accessWideners(file("src/main/resources/${mod_id}.accesswidener"))
}

dependencies {
    compileOnly("org.spongepowered:mixin:0.8.5")
    implementation("com.google.code.findbugs:jsr305:3.0.1")
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = base.archivesName.get()
            from(components["java"])
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}
