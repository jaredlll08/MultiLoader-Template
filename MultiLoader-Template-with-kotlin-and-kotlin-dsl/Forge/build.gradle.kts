import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    eclipse
    id("net.minecraftforge.gradle") version ("5.1.+")
    `maven-publish`
}

repositories {
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
}

val minecraftVersion: String by project
val forgeVersion: String by project
val forgeAtsEnabled: String by project
val modName: String by project
val modAuthor: String by project
val modId: String by project

val baseArchiveName = "${modName}-forge-${minecraftVersion}"

base {
    archivesName.set(baseArchiveName)
}

minecraft {
    mappings("official", minecraftVersion)

    if (forgeAtsEnabled.toBoolean()) {
        // This location is hardcoded in Forge and can not be changed.
        // https://github.com/MinecraftForge/MinecraftForge/blob/be1698bb1554f9c8fa2f58e32b9ab70bc4385e60/fmlloader/src/main/java/net/minecraftforge/fml/loading/moddiscovery/ModFile.java#L123
        accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))
        project.logger.debug("Forge Access Transformers are enabled for this project.")
    }

    runs {
        create("client") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            taskName("Client")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                }
            }
        }

        create("server") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            taskName("Server")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                }
            }
        }

        create("data") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args("--mod", modId, "--all", "--output", file("src/generated/resources/"), "--existing", file("src/main/resources/"))
            taskName("Data")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                    source(project(":Common").sourceSets.main.get())
                }
            }
        }
    }
}

sourceSets.main.get().resources.srcDir("src/generated/resources")

// Workaround for non-mc libraries in ForgeGradle
configurations {
    val library = maybeCreate("library")
    api.configure {
        extendsFrom(library)
    }
}
minecraft.runs.all {
    lazyToken("minecraft_classpath") {
        return@lazyToken configurations["library"].copyRecursive().resolve()
            .joinToString(File.pathSeparator) { it.absolutePath }
    }
}

val forgeKotlinVersion: String by project
val kotlinVersion: String by project
dependencies {
    minecraft("net.minecraftforge:forge:${minecraftVersion}-${forgeVersion}")
    implementation("thedarkcolour:kotlinforforge:${forgeKotlinVersion}")
    compileOnly(project(":Common"))

    val library = configurations["library"]
    library("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}") {
        exclude(group = "org.jetbrains", module = "annotations")
    }
    library("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}") {
        exclude(group = "org.jetbrains", module = "annotations")
    }
}

tasks.withType<KotlinCompile> {
    source(project(":Common").sourceSets.main.get().allSource)
}

tasks.processResources {
    from(project(":Common").sourceSets.main.get().resources)
}

tasks {
    jar {
        finalizedBy("reobfJar")
    }
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            artifactId = baseArchiveName
            artifact(tasks.jar)
        }
    }

    repositories {
        maven("file://${System.getenv("local_maven")}")
    }
}