/*
Copyright 2013 Johan Östling

This file is part of Rabalder.

Rabalder is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
import sbt._
import Keys._
import sbtassembly.Plugin._
import AssemblyKeys._

object OneGameAMonthBuild extends Build {

  lazy val projectSettings = Defaults.defaultSettings ++ Seq(
    libraryDependencies ++= Seq(
      "org.scalatest" %% "scalatest" % "2.0.M5" % "test",
      "org.mockito" % "mockito-all" % "1.9.5" % "test"
    ),
    version := "0.1"
  )

  lazy val customAssemblySettings = assemblySettings ++ Seq(
    excludedJars in assembly <<= (fullClasspath in assembly) map { cp => 
      cp filter {_.data.getName == "gdx-sources.jar"}
    }
  )
	
  // Start simple, can split it later
  // If I do split it, maybe keep the parent project referenced with file(.)

  lazy val desktop: Project = Project("rabalder", file(".")) settings(projectSettings ++ Seq(
    mainClass in Compile := Some("net.badgerclaw.onegameamonth.january.Main"),
    fork in run := true
  ) ++ customAssemblySettings :_*)

}
