val scala3Version = "3.0.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala3-simple",
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
    ),

    libraryDependencies += "org.scalatestplus" %% "mockito-3-4" % "3.2.9.0" % "test",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % Test ,
    libraryDependencies += "org.apache.commons" % "commons-configuration2" % "2.7",
    libraryDependencies += "commons-codec" % "commons-codec" % "20041127.091804",
    libraryDependencies += "commons-io" % "commons-io" % "20030203.000550",
)
