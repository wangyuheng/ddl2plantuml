val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project

plugins {
    application
    kotlin("jvm") version "1.9.23"
    kotlin("plugin.serialization") version "1.9.23"
    id("io.ktor.plugin") version "2.3.9"
}

group = "wang.yuheng"
version = "1.4.2"

application {
    mainClass.set("wang.yuheng.AppKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-freemarker-jvm")
    implementation("com.github.jsqlparser:jsqlparser:4.9")
    implementation("info.picocli:picocli:4.7.5")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

ktor {
    fatJar {
        archiveFileName.set("ddl2plantuml.jar")
    }

    docker {
        jreVersion.set(JavaVersion.VERSION_21)
        localImageName.set("wangyuheng/ddl2plantuml")
        imageTag.set("$version")
        portMappings.set(listOf(
//            io.ktor.plugin.features.DockerPortMapping(
//                8080,
//                8080,
//                io.ktor.plugin.features.DockerPortMappingProtocol.TCP
//            )
        ))
    }
}