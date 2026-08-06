import java.time.Instant

plugins {
    id("java")
    id("com.gradleup.shadow") version "9.6.1"
    id("io.freefair.lombok") version "9.5.0"
    id("com.github.ben-manes.versions") version "0.59.0"
    id("se.patrikerdes.use-latest-versions") version "0.2.19"
}

group = "com.bencodez"
version = "7.1.2-SNAPSHOT"

val projectName = "VotingPlugin"
val projectVersion = version as String

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(25))
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/groups/public")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://nexus.velocitypowered.com/repository/maven-public/")
    maven("https://nexus.bencodez.com/repository/maven-public/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://nexus.scarsz.me/content/groups/public/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:26.2-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.42")
    compileOnly("net.md-5:bungeecord-api:1.21-R0.4")
    compileOnly("com.vexsoftware:nuvotifier-universal:2.7.2")
    compileOnly("be.maximvdw:mvdwplaceholderapi:3.1.1")
    compileOnly("com.velocitypowered:velocity-api:3.4.0")
    compileOnly("me.clip:placeholderapi:2.12.2")
    compileOnly("com.discordsrv:discordsrv:1.30.4")

    implementation("org.bstats:bstats-velocity:3.2.1")
    implementation("com.bencodez:advancedcore:3.8.2-SNAPSHOT")
    implementation("org.spongepowered:configurate-core:4.2.0")
    implementation("org.spongepowered:configurate-yaml:4.2.0")
    implementation("org.spongepowered:configurate-gson:4.2.0")

    annotationProcessor("com.velocitypowered:velocity-api:3.4.0")

    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.12.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.12.2")
    testImplementation("org.mockito:mockito-core:5.21.0")
    testImplementation("org.mockito:mockito-junit-jupiter:5.23.0")
    testImplementation("org.spigotmc:spigot-api:26.2-R0.1-SNAPSHOT")
}

configurations.testImplementation { extendsFrom(configurations.compileOnly.get()) }

tasks.processResources {
    filesMatching(listOf("plugin.yml", "bungee.yml", "votingpluginversion.yml")) {
        expand(
            "timestamp" to Instant.now().toString(),
            "build" to mapOf(
                "profile" to mapOf("id" to "default"),
                "number" to "NOTSET"
            ),
            "project" to mapOf(
                "name" to projectName,
                "version" to projectVersion
            )
        )
    }
}

tasks.compileJava {
    options.encoding = "UTF-8"
    options.release.set(25)
}

tasks.test { useJUnitPlatform() }

tasks.shadowJar {
    archiveBaseName.set("VotingPlugin")
    archiveVersion.set("")
    archiveClassifier.set("")

    relocate("com.tcoded.folialib", "com.bencodez.votingplugin.simpleapi.folialib")
    relocate("com.bencodez.simpleapi", "com.bencodez.votingplugin.simpleapi")
    relocate("com.bencodez.advancedcore", "com.bencodez.votingplugin.advancedcore")
    relocate("net.pl3x.bukkit.chatapi", "com.bencodez.votingplugin")
    relocate("me.mrten.mysqlapi", "com.bencodez.votingplugin.mysqlapi")
    relocate("com.zaxxer.hikari", "com.bencodez.votingplugin.simpleapi.hikari")
    relocate("org.bstats", "com.bencodez.votingplugin.bstats")
    relocate("xyz.upperlevel.spigot", "com.bencodez.votingplugin.advancedcore.xyz.upperlevel.spigot")
    relocate("org.spongepowered.configurate", "com.bencodez.simpleapi.configurate")
    relocate("io.leangen.geantyref", "com.bencodez.simpleapi.geantyref")

    dependencies { exclude(dependency("com.google.*:.*")) }

    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA", "module-info.class")
    mergeServiceFiles()

    manifest { attributes["paperweight-mappings-namespace"] = "mojang" }
}

tasks.build { dependsOn(tasks.shadowJar) }
