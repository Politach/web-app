import sbt._
import sbt.Keys._
import akka.sbt.AkkaKernelPlugin
import akka.sbt.AkkaKernelPlugin.{ Dist, outputDirectory, distJvmOptions, distBootClass }
import spray.revolver.RevolverPlugin._
import org.flywaydb.sbt.FlywayPlugin._

object Build extends sbt.Build {
  val Organization = "Politach"
  val Version = "0.1-SNAPSHOT"
  val ScalaVersion = "2.11.5"

  lazy val buildSettings =
    Defaults.defaultSettings ++
      Seq(
        organization         := Organization,
        version              := Version,
        scalaVersion         := ScalaVersion,
        crossPaths           := false,
        organizationName     := Organization,
        organizationHomepage := Some(url("https://politach.com"))
      )

  lazy val defaultSettings =
    buildSettings ++
      Seq(
        resolvers                 ++= Resolvers.seq,
        scalacOptions             ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-feature", "-language:higherKinds"),
        javaOptions               ++= Seq("-Dfile.encoding=UTF-8"),
        javacOptions              ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation"),
        parallelExecution in Test :=  false,
        fork              in Test :=  true
      )

  lazy val root = Project(
    "politach",
    file("."),
    settings =
      defaultSettings               ++
      AkkaKernelPlugin.distSettings ++
      Revolver.settings             ++
      flywaySettings                ++
      Seq(
        libraryDependencies                       ++= Dependencies.root,
        distJvmOptions       in Dist              :=  "-server -Xms256M -Xmx1024M",
        distBootClass        in Dist              :=  "com.politach.ApiKernel",
        outputDirectory      in Dist              :=  file("target/dist"),
        Revolver.reStartArgs                      :=  Seq("com.politach.Main"),
        mainClass            in Revolver.reStart  :=  Some("com.politach.Main"),
        autoCompilerPlugins                       :=  true,
        scalacOptions        in (Compile,doc)     :=  Seq("-groups", "-implicits", "-diagrams"),
        flywaySchemas := Seq("public"),
        flywayLocations := Seq("sql/migration")
      )
  ).settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)
}
