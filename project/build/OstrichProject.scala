import sbt._

import com.twitter.sbt._

class OstrichProject(info: ProjectInfo) extends DefaultProject(info) {
  val specs = "org.scala-tools.testing" % "specs_2.8.0" % "1.6.5"
  val testinterface = "org.scala-tools.testing" % "test-interface" % "0.5" 
  
  val vscaladoc = "org.scala-tools" % "vscaladoc" % "1.1-md-3"
  // val twitterJson = "com.twitter" % "json" % "2.1.3"
  val configgy = "net.lag" % "configgy" % "2.0.0"
  val commonsLogging = "commons-logging" % "commons-logging" % "1.1"
  val commonsLang = "commons-lang" % "commons-lang" % "2.2"
  val mockito = "org.mockito" % "mockito-core" % "1.8.4"
  val hamcrest = "org.hamcrest" % "hamcrest-all" % "1.1"
  // val xrayspecs = "com.twitter" %% "xrayspecs" % "2.0"
  val cglib = "cglib" % "cglib" % "2.1_3"
  val asm = "asm" % "asm" % "1.5.3"
  val objenesis = "org.objenesis" % "objenesis" % "1.1"
  val netty = "org.jboss.netty" % "netty" % "3.1.5.GA"

  val jbossRepo = "JBoss Repository" at "http://repository.jboss.org/nexus/content/groups/public/"

}
