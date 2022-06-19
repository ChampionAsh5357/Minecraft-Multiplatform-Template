import net.fabricmc.loom.configuration.ide.RunConfigSettings
import org.gradle.configurationcache.extensions.capitalized

plugins {
    java
    alias(libs.plugins.fabric.loom)
    alias(libs.plugins.licenser)
}

base.archivesName.set("${base.archivesName.get()}-${extra["fabric.id"] as String}")

loom.runs {
    mapOf(
        "client" to RunConfigSettings::client,
        "server" to RunConfigSettings::server
    ).forEach { (key, config) ->
        named(key) {
            config.invoke(this)
            configName = "Fabric${key.capitalized()}"
            ideConfigGenerated(true)
            runDir("../../run/${key}")
        }
    }
}

dependencies {
    "minecraft"(libs.minecraft)
    "mappings"(loom.officialMojangMappings())
    "modImplementation"(libs.bundles.fabric)

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
        "base_group",
        "platform_id",
        "fabric_id",
        "fabric_loader",
        "fabric_api",
        "minecraft_current",
        "java"
    ).associateWith { rootProject.extra[it.replace("_", ".")] as String }

    inputs.properties(props)
    filesMatching("**/fabric.mod.json") {
        expand(props)
    }
}
