import org.cadixdev.gradle.licenser.Licenser
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

plugins {
    java
    alias(libs.plugins.licenser)
}

extra["base.year.current"] = Calendar.getInstance().get(Calendar.YEAR).toString()
extra["java"] = libs.versions.java.get()
extra["forge.loader.spec"] = libs.versions.forge.loader.spec.get()
extra["forge.loader.impl"] = libs.versions.forge.loader.impl.get()
extra["fabric.loader"] = libs.versions.fabric.loader.get()
extra["fabric.api"] = libs.versions.fabric.api.get()
extra["quilt.loader"] = libs.versions.quilt.loader.get()
extra["quilted.fabric.api"] = libs.versions.quilted.fabric.api.get()
extra["minecraft.current"] = libs.versions.minecraft.current.get()
extra["minecraft.next"] = libs.versions.minecraft.next.get()
extra["minecraft.pack.resources"] = libs.versions.minecraft.pack.resources.get()
extra["minecraft.pack.data"] = libs.versions.minecraft.pack.data.get()

subprojects {
    repositories {
        mavenCentral()
    }

    plugins.withType<JavaPlugin> {
        base.archivesName.set(rootProject.extra["base.id"] as String)
        group = rootProject.extra["base.group"] as String
        version = rootProject.extra["base.version"] as String

        java {
            toolchain.languageVersion.set(JavaLanguageVersion.of(libs.versions.java.get()))

            withSourcesJar()
            withJavadocJar()
        }

        tasks.compileJava {
            options.encoding = rootProject.extra["file.encoding"] as String
        }

        tasks.javadoc {
            options {
                encoding = rootProject.extra["file.encoding"] as String
                if (this is StandardJavadocDocletOptions)
                    tags(
                        "apiNote:a:API Note:",
                        "implSpec:a:Implementation Requirements:",
                        "implNote:a:Implementation Note:"
                    )
            }
        }

        afterEvaluate {
            if (this.name.contains(rootProject.extra["platform.id"] as String) && !this.name.contains(rootProject.extra["core.id"] as String)) {
                tasks.withType<JavaCompile> {
                    source(rootProject.project("${rootProject.extra["platform.id"] as String}:${rootProject.extra["platform.id"] as String}-${rootProject.extra["core.id"] as String}").sourceSets.main.get().allSource)
                    source(rootProject.project(rootProject.extra["common.id"] as String).sourceSets.main.get().allSource)
                }

                tasks.named<Copy>("processResources") {
                    from(rootProject.project("${rootProject.extra["platform.id"] as String}:${rootProject.extra["platform.id"] as String}-${rootProject.extra["core.id"] as String}").sourceSets.main.get().resources) {
                        val props = listOf(
                            "base_name",
                            "minecraft_pack_resources",
                            "minecraft_pack_data"
                        ).associateWith { rootProject.extra[it.replace("_", ".")] as String }

                        inputs.properties(props)
                        expand(props)
                    }
                    from(rootProject.project(rootProject.extra["common.id"] as String).sourceSets.main.get().resources) {
                        val props = listOf(
                            "base_id"
                        ).associateWith { rootProject.extra[it.replace("_", ".")] as String }

                        inputs.properties(props)
                        expand(props)
                    }
                }
            }

            tasks.jar {
                from(rootProject.file("LICENSE")) {
                    val props = listOf(
                        "base_year_start",
                        "base_year_current",
                        "base_author"
                    ).associateWith { rootProject.extra[it.replace("_", ".")] as String }

                    inputs.properties(props)
                    expand(props)
                }

                manifest.attributes(mapOf(
                    "Specification-Title" to rootProject.extra["base.id"] as String,
                    "Specification-Vendor" to rootProject.extra["base.author"] as String,
                    "Specification-Version" to rootProject.extra["base.version"] as String,
                    "Implementation-Title" to base.archivesName.get(),
                    "Implementation-Vendor" to rootProject.extra["base.author"] as String,
                    "Implementation-Version" to project.version,
                    "Implementation-Timestamp" to DateTimeFormatter.ISO_INSTANT.format(Instant.now()),
                ))
            }
        }
    }

    plugins.withType<Licenser> {
        license {
            header.set(resources.text.fromFile(rootProject.file("HEADER")))

            properties {
                listOf(
                    "base_author",
                    "license_spdx"
                ).forEach { set(it, rootProject.extra[it.replace("_", ".")] as String) }
            }

            include("**/*.java")
        }
    }
}
