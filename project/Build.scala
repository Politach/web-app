import sbt._
import sbt.Keys._
import akka.sbt.AkkaKernelPlugin
import akka.sbt.AkkaKernelPlugin.{ Dist, outputDirectory, distJvmOptions, distBootClass }
import spray.revolver.RevolverPlugin._

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
        initialize ~= { _ =>
          sys.props("scalac.patmat.analysisBudget") = "off"
          if (sys.props("java.specification.version") != "1.8")
            sys.error("Java 8 is required for this project.")
        },
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
      Seq(
        libraryDependencies                       ++= Dependencies.root,
        distJvmOptions       in Dist              :=  "-server -Xms256M -Xmx1024M",
        distBootClass        in Dist              :=  "com.politach.ApiKernel",
        outputDirectory      in Dist              :=  file("target/dist"),
        Revolver.reStartArgs                      :=  Seq("com.politach.Main"),
        mainClass            in Revolver.reStart  :=  Some("com.politach.Main"),
        autoCompilerPlugins                       :=  true,
        scalacOptions        in (Compile,doc)     :=  Seq("-groups", "-implicits", "-diagrams")
      )
  ).settings(net.virtualvoid.sbt.graph.Plugin.graphSettings: _*)
}
