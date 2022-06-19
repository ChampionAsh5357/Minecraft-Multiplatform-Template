plugins {
    java
    alias(libs.plugins.vanilla.gradle)
    alias(libs.plugins.licenser)
}

minecraft {
    version(libs.versions.minecraft.current.get())
}

dependencies {
    compileOnly(rootProject.project("${rootProject.extra["platform.id"] as String}:${rootProject.extra["platform.id"] as String}-${rootProject.extra["core.id"] as String}"))
}
