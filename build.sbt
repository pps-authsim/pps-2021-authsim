val scala3Version = "3.0.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "authsim",
    version := "0.1.0",

    scalaVersion := scala3Version,

    scalacOptions ++= Seq(
      "-Xfatal-warnings", // Fail on warnings.
      // Warning settings were introduced in 2.13. Most of them are not yet implemented in Scala 3.
      /*
      "-Wdead-code", // Warn when dead code is identified.
      "-Wextra-implicit", // Warn when more than one implicit parameter section is defined.
      "-Wnumeric-widen", // Warn when numerics are widened.
      "-Woctal-literal", // Warn on obsolete octal syntax.
      "-Wself-implicit", // Warn when an implicit resolves to an enclosing self-definition.
      "-Wunused:imports,patvars,privates,locals,explicits,implicits,params,linted", // Enable or disable specific unused warnings
      "-Wvalue-discard", // Warn when non-Unit expression results are unused.
      "-Xlint:adapted-args,nullary-unit,inaccessible,nullary-override,infer-any,missing-interpolator,doc-detached,private-shadow,type-parameter-shadow,poly-implicit-overload,option-implicit,delayedinit-select,package-object-classes,stars-align,constant,unused,nonlocal-return,implicit-not-found,serial,valpattern,eta-zero,eta-sam,deprecation", // Enable recommended warnings
      */
      "-deprecation", // Warn when deprecated api is used.
      "-unchecked", // Enable additional warnings where generated code depends on assumptions.
      "-explain", // Explain errors in more detail.
      "-new-syntax", // Require then and do in control expressions
      "-language:postfixOps",
      "-language:implicitConversions"
    ),

    // test

    libraryDependencies += "org.scalatestplus" %% "mockito-3-4" % "3.2.9.0" % Test,
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test ,

    // util

    libraryDependencies += "org.apache.commons" % "commons-configuration2" % "2.7",

    libraryDependencies += "commons-codec" % "commons-codec" % "20041127.091804",
    libraryDependencies += "commons-io" % "commons-io" % "20030203.000550",
    libraryDependencies += "com.google.guava" % "guava" % "25.1-jre",

    // in-memory databases
    libraryDependencies += "com.h2database" % "h2" % "1.4.200",
    libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.25",
    libraryDependencies += "de.flapdoodle.embed" % "de.flapdoodle.embed.mongo" % "3.0.0",
    libraryDependencies += "org.immutables" % "value" % "2.8.8",
    libraryDependencies += ("org.mongodb.scala" %% "mongo-scala-driver" % "4.3.2").cross(CrossVersion.for3Use2_13),

    // gui
    libraryDependencies += "org.scalafx" %% "scalafx" % "16.0.0-R24",
    libraryDependencies ++= {
      // Determine OS version of JavaFX binaries
      lazy val osName = System.getProperty("os.name") match {
        case n if n.startsWith("Linux") => "linux"
        case n if n.startsWith("Mac") => "mac"
        case n if n.startsWith("Windows") => "win"
        case _ => throw new Exception("Unknown platform!")
      }
      Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
        .map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)
    }
)

// Build configuration

ThisBuild / assemblyMergeStrategy := {
  case PathList("META-INF", xs@_*) =>
    xs.map(_.toLowerCase) match {
      case ("manifest.mf" :: Nil) |
           ("index.list" :: Nil) |
           ("dependencies" :: Nil) |
           ("license" :: Nil) |
           ("notice" :: Nil) => MergeStrategy.discard
      case _ => MergeStrategy.first
    }
  case "reference.conf" => MergeStrategy.concat
  case _ => MergeStrategy.first
}

ThisBuild/ assemblyJarName := "authsim.jar"

ThisBuild / mainClass := Some("it.unibo.authsim.client.app.AuthsimApp")

