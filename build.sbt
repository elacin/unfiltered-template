organization := "templates"
name         := "unfiltered"
version      := "0.1-SNAPSHOT"
scalaVersion := "2.11.7"
scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-unchecked", "-Yno-adapted-args", "-Ywarn-dead-code", "-Ywarn-numeric-widen", "-Ywarn-value-discard", "-Xfuture", "-explaintypes" )

val unfilteredVersion = "0.8.4"

libraryDependencies ++= Seq(
  "com.jteigen"                %% "linx"                      % "0.2",
  "com.typesafe.scala-logging" %% "scala-logging"             % "3.0.0",
  "io.argonaut"                %% "argonaut"                  % "6.1",
  "javax.servlet"               % "javax.servlet-api"         % "3.1.0",
  "net.databinder.dispatch"    %% "dispatch-core"             % "0.11.3",
  "net.databinder"             %% "unfiltered-filter"         % unfilteredVersion,
  "net.databinder"             %% "unfiltered"                % unfilteredVersion,
  "net.databinder"             %% "unfiltered-filter-async"   % unfilteredVersion,
  "net.databinder"             %% "unfiltered-filter-uploads" % unfilteredVersion,
  "net.databinder"             %% "unfiltered-jetty"          % unfilteredVersion,
  "org.slf4j"                   % "slf4j-simple"              % "1.7.12",

  "org.scalatest"              %% "scalatest"                 % "3.0.0-M10" % Test
)

