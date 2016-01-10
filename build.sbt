name := "vaadin-demo"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Scaladin Snapshots" at "http://henrikerola.github.io/repository/snapshots/"

libraryDependencies ++= Seq(
  "org.vaadin.addons" %% "scaladin" % "3.2-SNAPSHOT",
  "com.vaadin" % "vaadin-server" % "7.5.10",
  "com.vaadin" % "vaadin-client-compiled" % "7.5.10",
  "com.vaadin" % "vaadin-themes" % "7.5.10"
)

enablePlugins(JettyPlugin)