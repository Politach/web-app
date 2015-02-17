import sbt._

object Dependencies {
  object V {
    val akka = "2.3.9"
    val akkaExperimental = "1.0-M3"
    val scalaz = "7.1.1"
    val slick = "3.0.0-M1"
  }

  object Compile {
    val akkaActor       = "com.typesafe.akka"             %% "akka-actor"                    % V.akka
    val akkaKernel      = "com.typesafe.akka"             %% "akka-kernel"                   % V.akka
    val akkaStream      = "com.typesafe.akka"             %% "akka-stream-experimental"      % V.akkaExperimental
    val akkaHttp        = "com.typesafe.akka"             %% "akka-http-experimental"        % V.akkaExperimental
    val akkaHttpCore    = "com.typesafe.akka"             %% "akka-http-core-experimental"   % V.akkaExperimental
    val akkaHttpSpray   = "com.typesafe.akka"             %% "akka-http-spray-json-experimental" % V.akkaExperimental
    val akkaSlf4j       = "com.typesafe.akka"             %% "akka-slf4j"                    % V.akka

    val sprayJson       = "io.spray"                      %% "spray-json"                    % "1.3.1"
    val json4s          = "org.json4s"                    %% "json4s-jackson"                % "3.2.11"

    val postgresJdbc    = "org.postgresql"                %  "postgresql"                    % "9.4-1200-jdbc41" exclude("org.slf4j", "slf4j-simple")
    val slick           = "com.typesafe.slick"            %% "slick"                         % V.slick
    val flywayCore      = "org.flywaydb"                  %  "flyway-core"                   % "3.1"
    val hikariCP        = "com.zaxxer"                    %  "HikariCP"                      % "2.3.2"

    val scalazCore      = "org.scalaz"                    %% "scalaz-core"                   % V.scalaz
    val scalazConcurrent = "org.scalaz"                   %% "scalaz-concurrent"             % V.scalaz

    val jodaTime        = "joda-time"                     %  "joda-time"                     % "2.7"
    val jodaConvert     = "org.joda"                      %  "joda-convert"                  % "1.7"

    val logbackClassic  = "ch.qos.logback"                % "logback-classic"                % "1.1.2"
    val scalaLogging    = "com.typesafe.scala-logging"    %% "scala-logging"                 % "3.1.0"
  }

  object Test {
    val akkaTestkit     = "com.typesafe.akka"             %% "akka-testkit"                  % V.akka % "test"
    val scalacheck      = "org.scalacheck"                %% "scalacheck"                    % "1.12.2" % "test"
    val specs2          = "org.specs2"                    %% "specs2-core"                   % "2.4.15" % "test"
    val slickTestkit    = "com.typesafe.slick"            %% "slick-testkit"                 % V.slick % "test"
  }

  import Compile._, Test._

  val root = Seq(
    akkaSlf4j, akkaActor, akkaKernel, akkaStream, scalazCore, scalazConcurrent,
    sprayJson, json4s,
    postgresJdbc, slick, flywayCore, hikariCP,
    logbackClassic, scalaLogging, jodaTime, jodaConvert,
    akkaTestkit, scalacheck, specs2, slickTestkit
  )
}
