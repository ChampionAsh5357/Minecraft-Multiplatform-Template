plugins {
    java
    alias(libs.plugins.vanilla.gradle)
    alias(libs.plugins.licenser)
}

minecraft {
    version(libs.versions.minecraft.current.get())
}
