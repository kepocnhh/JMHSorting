import java.time.Duration
import java.time.temporal.ChronoUnit

repositories.mavenCentral()

version = Version.lib

plugins {
    id("org.jetbrains.kotlin.jvm")
}

tasks.getByName<JavaCompile>("compileJava") {
    targetCompatibility = Version.jvmTarget
}

tasks.getByName<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>("compileKotlin") {
    kotlinOptions.jvmTarget = Version.jvmTarget
}

sourceSets.create("jmh") {
    project.kotlin.target.compilations.also {
        it[name].associateWith(it["main"])
    }
}

dependencies {
    "jmhImplementation"("org.openjdk.jmh:jmh-core:${Version.jmh}")
    "jmhImplementation"("org.openjdk.jmh:jmh-generator-bytecode:${Version.jmh}")
}

project.kotlin.target.compilations.getByName("jmh") {
    val issuer = name
    val outputSourceDir = File(buildDir, "${issuer}Generated/sources")
    val outputResourceDir = File(buildDir, "${issuer}Generated/resources")
    val outputClassesDir = File(buildDir, "${issuer}Generated/classes")
    val generatorType = "default"
    val generators = output.classesDirs.map {
        val compiledBytecodePath = it.absolutePath
        // Usage: generator <compiled-bytecode-dir> <output-source-dir> <output-resource-dir> [generator-type]
        task<JavaExec>("${issuer}RunBytecodeGenerator${compiledBytecodePath.hashCode()}") {
            dependsOn("classes")
            mainClass.set("org.openjdk.jmh.generators.bytecode.JmhBytecodeGenerator")
            classpath = sourceSets[issuer].runtimeClasspath
            args(
                compiledBytecodePath,
                outputSourceDir.absolutePath,
                outputResourceDir.absolutePath,
                generatorType
            )
        }
    }
    val compileGeneratedTask = task<JavaCompile>("${issuer}CompileGenerated") {
        dependsOn(generators)
        classpath = sourceSets[issuer].runtimeClasspath
        source(outputSourceDir)
        destinationDirectory.set(outputClassesDir)
    }
    mapOf(
        "runBenchmark" to 1,
        "runBenchmarkMultiThread" to Runtime.getRuntime().availableProcessors(),
    ).forEach { (taskName, threads) ->
        task<JavaExec>(taskName) {
            dependsOn(compileGeneratedTask)
            doFirst {
                File(buildDir, "reports/jmh").mkdirs()
            }
            mainClass.set("org.openjdk.jmh.Main")
            classpath(
                sourceSets[issuer].runtimeClasspath,
                outputResourceDir,
                outputClassesDir
            )
            val args = JMHArgs(
                timeout = Duration.of(10, ChronoUnit.SECONDS),
                forks = 1,
                iterations = 1,
                time = Duration.of(1, ChronoUnit.SECONDS),
                warmup = JMHArgs.Warmup(
                    forks = 1,
                    iterations = 1,
                    time = Duration.of(1, ChronoUnit.SECONDS)
                ),
                mode = BenchmarkMode.AverageTime,
                timeUnit = JMHArgs.TimeUnit.ms,
                threads = threads,
//                output = File(buildDir, "reports/jmh/result.txt")
            )
            args(args.toArgs())
        }
    }
}
