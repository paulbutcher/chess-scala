organization := "com.paulbutcher"

name := "chess"

version := "0.1-SNAPSHOT"

scalaVersion := "2.10.3"

scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature")

fork in run := true

javaOptions in run ++= Seq("-Xms12G", "-Xmx12G", "-XX:NewRatio=8")