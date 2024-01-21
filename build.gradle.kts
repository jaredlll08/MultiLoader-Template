import java.text.SimpleDateFormat
import java.util.*

plugins {
    java
    // Required for NeoGradle
    id("org.jetbrains.gradle.plugin.idea-ext") version "1.1.7"
}

subprojects {
    val mod_name: String by project
    val mod_author: String by project
    val minecraft_version: String by project

    apply(plugin = "java")

    extensions.configure<JavaPluginExtension> {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        withSourcesJar()
        withJavadocJar()
    }

    tasks.jar {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_$mod_name" }
        }

        manifest {
            attributes(
                    "Specification-Title" to mod_name,
                    "Specification-Vendor" to mod_author,
                    "Specification-Version" to archiveVersion,
                    "Implementation-Title" to project.name,
                    "Implementation-Version" to archiveVersion,
                    "Implementation-Vendor" to mod_author,
                    "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
                    "Timestamp" to System.currentTimeMillis(),
                    "Built-On-Java" to "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                    "Built-On-Minecraft" to minecraft_version
            )
        }
    }

    repositories {
        mavenCentral()
        maven("https://repo.spongepowered.org/repository/maven-public/") { name = "Sponge / Mixin" }
        maven("https://maven.blamejared.com") { name = "BlameJared Maven (JEI / CraftTweaker / Bookshelf)" }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    
    tasks.processResources {
        val version: String by project
        val group: String by project
        val forge_version: String by project
        val forge_loader_version_range: String by project
        val forge_version_range: String by project
        val minecraft_version_range: String by project
        val fabric_version: String by project
        val fabric_loader_version: String by project
        val mod_id: String by project
        val license: String by project
        val description: String by project
        val neoforge_version: String by project
        val neoforge_loader_version_range: String by project
        val credits: String by project
        
        val expandProps = mapOf(
                "version" to version, 
                "group" to group, //Else we target the task's group.
                "minecraft_version" to minecraft_version,
                "forge_version" to forge_version,
                "forge_loader_version_range" to forge_loader_version_range,
                "forge_version_range" to forge_version_range,
                "minecraft_version_range" to minecraft_version_range,
                "fabric_version" to fabric_version,
                "fabric_loader_version" to fabric_loader_version,
                "mod_name" to mod_name,
                "mod_author" to mod_author,
                "mod_id" to mod_id,
                "license" to license,
                "description" to description,
                "neoforge_version" to neoforge_version,
                "neoforge_loader_version_range" to neoforge_loader_version_range,
                "credits" to credits
        )

        filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "META-INF/mods.toml", "*.mixins.json")) {
            expand(expandProps)
        }
        inputs.properties(expandProps)
    }

    // Disables Gradle's custom module metadata from being published to maven. The
    // metadata includes mapped dependencies which are not reasonably consumable by
    // other mod developers.
    tasks.withType<GenerateModuleMetadata> {
        enabled = false
    }
}
