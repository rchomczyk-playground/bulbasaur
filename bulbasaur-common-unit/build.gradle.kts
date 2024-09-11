plugins {
    `bulbasaur-java`
    `bulbasaur-unit`
    `bulbasaur-repositories`
}

dependencies {
    testImplementation(project(":bulbasaur-common"))
}