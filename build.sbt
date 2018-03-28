
lazy val sharedSettings = Seq(
  name := "flashbook-server",
  organization := "io.flashbook",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.8"
)

lazy val akkaVersion = "2.5.11"
lazy val akkaHttpVersion = "10.1.0"
lazy val circeVersion = "0.9.0"

lazy val akkaDeps = List(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,

  // Persistent storage
  "com.typesafe.akka" %% "akka-persistence" % akkaVersion,
  "org.fusesource.leveldbjni" % "leveldbjni-all" % "1.8",

  "com.github.andyglow" %% "websocket-scala-client" % "0.2.4" % Compile,

  "de.heikoseeberger" %% "akka-http-circe" % "1.20.0"

  // Serialization
  //  "com.github.romix.akka" %% "akka-kryo-serialization" % "0.5.0"
)

lazy val testDeps = List(
  "org.scalactic" %% "scalactic" % "3.0.5",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

lazy val graphQLDeps = List(
  "org.sangria-graphql" %% "sangria" % "1.4.0",
  "org.sangria-graphql" %% "sangria-circe" % "1.2.1",
  "org.sangria-graphql" %% "sangria-akka-streams" % "1.0.0"
)

lazy val jsonDeps = List(
  "io.circe" %% "circe-core" % circeVersion,
  "io.circe" %% "circe-generic" % circeVersion,
  "io.circe" %% "circe-parser" % circeVersion,
  "io.circe" %% "circe-optics" % circeVersion
)

lazy val dataStores = List(
  "net.openhft" % "chronicle-queue" % "4.6.73",
  "net.openhft" % "chronicle-map" % "3.14.5"
)

lazy val server = project
  .in(file("server"))
  .settings(sharedSettings: _*)
  .settings(name := "flashbook-server")
  .settings(libraryDependencies ++= (akkaDeps ++ jsonDeps ++ graphQLDeps ++ dataStores ++ testDeps))
  .dependsOn(common)
  .aggregate(common)

//lazy val ui = project
//  .in(file("ui"))
//  .settings(sharedSettings: _*)
//  .settings(name := "doomsday-ui")
//  .settings(mainClass in Compile := Some("ui.Main"))
//  .settings(scalaJSUseMainModuleInitializer in Compile := true)
//  .enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin)
//    .settings(npmDependencies in Compile ++= Seq(
//      "react" -> "16.2.0",
//      "react-dom" -> "16.2.0"
//    ))
//  .dependsOn(common)
//  .aggregate(common)

lazy val common = project
  .in(file("common"))
  .settings(sharedSettings: _*)
  .settings(libraryDependencies ++= akkaDeps ++ jsonDeps ++ graphQLDeps ++ dataStores ++ testDeps)

lazy val root = project.in(file("."))
    .settings(sharedSettings: _*)
    .aggregate(server)

//lazy val root = project.in(file("."))
//    .aggregate(doomsdayJS, doomsdayJVM)
//    .settings(
//      publish := {},
//      publishLocal := {}
//    )

//lazy val doomsday = crossProject.in(file("."))
//  .settings(
//    name := "doomsday",
//    version := "0.1-SNAPSHOT"
////    scalaJSUseMainModuleInitializer := true
//  )
//  .jvmSettings(
//    libraryDependencies ++= (akkaDeps ++ Seq(
//      "org.scalactic" %% "scalactic" % "3.0.5",
//      "org.scalatest" %% "scalatest" % "3.0.5" % "test",
//      "org.sangria-graphql" %% "sangria" % "1.4.0"
//    ))
//  )
//    .jvmSettings(moduleName := "jvm")
//  .jsSettings(
//    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "0.9.1"
//  )
//  .jsSettings(moduleName := "js")

//lazy val doomsdayJS = doomsday.js
//lazy val doomsdayJVM = doomsday.jvm
