import org.gradle.configurationcache.extensions.capitalized

plugins {
    java
    alias(libs.plugins.forge.gradle)
    alias(libs.plugins.licenser)
}

base.archivesName.set("${base.archivesName.get()}-${extra["forge.id"] as String}")
version = "${libs.versions.minecraft.current.get()}-${version}"

minecraft {
    mappings("official", libs.versions.minecraft.current.get())

    runs {
        listOf(
            "client",
            "server"
        ).forEach {
            create(it) {
                workingDirectory(rootProject.file("run/${it}"))
                ideaModule = "${rootProject.name}.${extra["platform.id"] as String}.${project.name}.main"
                taskName("Forge${it.capitalized()}")
                mods.create(project.name).source(sourceSets.main.get())
            }
        }
    }
}

dependencies {
    "minecraft"(libs.forge.loader)

    compileOnly(rootProject.project("${rootProject.extra["platform.id"] as String}:${rootProject.extra["platform.id"] as String}-${rootProject.extra["core.id"] as String}"))
    compileOnly(rootProject.project(rootProject.extra["common.id"] as String))
}

tasks.named<Copy>("processResources") {
    val props = listOf(
        "license_id",
        "base_id",
        "base_version",
        "base_name",
        "base_author",
        "base_description",
        "forge_loader_spec",
        "forge_loader_impl",
        "minecraft_current",
        "minecraft_next"
    ).associateWith { rootProject.extra[it.replace("_", ".")] as String }

    inputs.properties(props)
    filesMatching("**/mods.toml") {
        expand(props)
    }
}

tasks.jar {
    finalizedBy("reobfJar")
}
