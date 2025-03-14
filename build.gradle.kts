plugins {
    id("net.labymod.labygradle")
    id("net.labymod.labygradle.addon")
}

val versions = providers.gradleProperty("net.labymod.minecraft-versions").get().split(";")

group = "net.niure.addons.nick"
version = providers.environmentVariable("VERSION").getOrElse("1.0.0")

labyMod {
    defaultPackageName = "net.niure.addons.nick" //change this to your main package name (used by all modules)

    minecraft {
        registerVersion(versions.toTypedArray()) {
            runs {
                getByName("client") {
                    // When the property is set to true, you can log in with a Minecraft account
                    // devLogin = true
                }
            }
        }
    }

    addonInfo {
        namespace = "ghdnick"
        displayName = "Mirror"
        author = "niure"
        description = "Display the current nickname and highlight your current nickname in the chat."
        minecraftVersion = "*"
        version = rootProject.version.toString()
    }
}

subprojects {
    plugins.apply("net.labymod.labygradle")
    plugins.apply("net.labymod.labygradle.addon")

    dependencies {
        testImplementation("org.mockito:mockito-core:3.11.2")

        testImplementation("org.junit.jupiter:junit-jupiter-api:5.4.0")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.4.0")
    }

    group = rootProject.group
    version = rootProject.version
}