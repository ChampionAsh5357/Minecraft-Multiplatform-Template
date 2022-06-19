rootProject.name = extra["base.id"] as String

pluginManagement.repositories {
    gradlePluginPortal()
    maven {
        name = "Sponge"
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
    }
    maven {
        name = "Forge"
        url = uri("https://maven.minecraftforge.net/")
    }
    maven {
        name = "Fabric"
        url = uri("https://maven.fabricmc.net/")
    }
    maven {
        name  = "Quilt"
        url = uri("https://maven.quiltmc.org/repository/release")
    }
}

listOf(
    "core.id",
    "common.id",
    "forge.id",
    "fabric.id",
    "quilt.id"
).forEach { include(if (it.contains("common")) extra[it] as String else "${extra["platform.id"] as String}:${extra["platform.id"] as String}-${extra[it] as String}") }
