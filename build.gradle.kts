plugins {
    id("com.android.application") version "8.2.2" apply false
    id("com.android.library") version "8.2.2" apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
}

// allprojects bloğunu kaldırdım çünkü
// dependencyResolutionManagement ile çakışıyor

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}