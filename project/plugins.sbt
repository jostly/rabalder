resolvers += Resolver.url("artifactory", url("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-releases"))(Resolver.ivyStylePatterns)

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.8.5")

addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "latest.integration")

resolvers += Resolver.url("sbt-plugin-snapshots", new URL("http://scalasbt.artifactoryonline.com/scalasbt/sbt-plugin-snapshots"))(sbt.Resolver.ivyStylePatterns)


//addSbtPlugin("org.scala-sbt" %% "sbt-android-plugin" % "0.6.2-SNAPSHOT")

