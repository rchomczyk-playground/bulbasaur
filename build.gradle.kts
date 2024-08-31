plugins {
    java
    `maven-publish`
}

group = "dev.shiza"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    withSourcesJar()
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

publishing {
    repositories {
        mavenLocal()
        maven(
            name = "shiza",
            url = "https://repo.shiza.dev",
            username = "MAVEN_USERNAME",
            password = "MAVEN_PASSWORD"
        )
    }

    publications {
        create<MavenPublication>("maven") {
            artifactId = "bulbasaur"
            from(project.components["java"])
        }
    }
}

fun RepositoryHandler.maven(
    name: String,
    url: String,
    username: String,
    password: String,
    snapshots: Boolean = true
) {
    val isSnapshot = version.toString().endsWith("-SNAPSHOT")
    if (isSnapshot && !snapshots) {
        return
    }

    this.maven {
        this.name =
            if (isSnapshot) "${name}Snapshots" else "${name}Releases"
        this.url =
            if (isSnapshot) uri("$url/snapshots") else uri("$url/releases")
        this.credentials {
            this.username = System.getenv(username)
            this.password = System.getenv(password)
        }
    }
}

sourceSets {
    main {
        java.setSrcDirs(listOf("src"))
        resources.setSrcDirs(emptyList<String>())
    }
    test {
        java.setSrcDirs(listOf("test"))
        resources.setSrcDirs(emptyList<String>())
    }
}

tasks {
    test {
        useJUnitPlatform()
    }
}
