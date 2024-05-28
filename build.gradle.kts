plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
}


subprojects {
    configurations.all {
        resolutionStrategy {
            eachDependency {
                if (requested.group == "androidx.compose.material3" && requested.name.startsWith("material3")) {
                    useVersion(libs.versions.material3Ref.get())
                }
            }
        }
    }
}