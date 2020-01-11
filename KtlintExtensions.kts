import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

ktlint {
    version.set("0.36.0")
    debug.set(true)
    verbose.set(true)
    android.set(true)
    outputToConsole.set(true)
    outputColorName.set("RED")
    ignoreFailures.set(true)
    enableExperimentalRules.set(true)
    additionalEditorconfigFile.set(file("/some/additional/.editorconfig"))
    disabledRules.set(setOf("final-newline"))
    reporters {
        reporter(ReporterType.PLAIN)
        reporter(ReporterType.CHECKSTYLE)

        customReporters {
            register("html") {
                fileExtension = "html"
                dependency = "me.cassiano:ktlint-html-reporter:0.2.3"
            }
        }
    }
    kotlinScriptAdditionalPaths {
        include(fileTree("scripts/"))
    }
    filter {
        exclude("**/generated/**")
        include("**/kotlin/**")
    }
}

dependencies {
    ktlintRuleset("com.github.username:rulseset:master-SNAPSHOT")
    ktlintRuleset(files("/path/to/custom/rulseset.jar"))
    ktlintRuleset(project(":chore:project-ruleset"))
}
