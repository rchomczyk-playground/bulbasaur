plugins {
    `bulbasaur-java-with-resources`
    `bulbasaur-publish`
    `bulbasaur-repositories`
}

dependencies {
    implementation(project(":bulbasaur-common"))
    implementation("com.squareup:javapoet:1.13.0")
    compileOnly("com.google.auto.service:auto-service:1.1.1")
}