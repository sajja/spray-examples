name := "Spray examples"

description := "Spray examples"

val akkaV = "2.3.6"

val sprayV = "1.3.2"

libraryDependencies ++= Seq(
  "io.spray" %% "spray-can" % sprayV withSources() withJavadoc(),
  "io.spray" %% "spray-routing" % sprayV withSources() withJavadoc(),
  "io.spray" %% "spray-json" % "1.3.1",
  "io.spray" %% "spray-testkit" % sprayV % "test",
  "com.typesafe.akka" %% "akka-actor" % akkaV,
  "com.typesafe.akka" %% "akka-testkit" % akkaV % "test"
)

scalacOptions ++= Seq("-feature", "-language:postfixOps")
