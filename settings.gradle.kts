pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven{
            url = uri("https://jcenter.bintray.com")
            maven { url = uri("https://jitpack.io") }
        }
        jcenter()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
    }
}

rootProject.name = "semesta_mitra_sejahtera"
include(":app")
 