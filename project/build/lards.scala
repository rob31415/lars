import sbt._
thgth
class lars(info: ProjectInfo) extends DefaultWebProject(info) {
//  val jetty6 = "org.mortbay.jetty" % "jetty" % "6.1.14" % "test" 
  val servletApi = "javax.servlet" % "servlet-api" % "2.5"

  val vaadin = "com.vaadin" % "vaadin" % "6.7.1"  //660

  // val postgresql = "postgresql" % "postgresql" % "8.4-702.jdbc4"
  val postgresql = "postgresql" % "postgresql" % "9.2-1002.jdbc4"
  val mybatis = "org.mybatis" % "mybatis" % "3.2.1"

  val log4jOverSlf4j = "org.slf4j" % "slf4j-log4j12" % "1.6.1"

  val specs = "org.specs2" %% "specs2" % "1.6.1"
  val specs2 = "org.specs2" %% "specs2-scalaz-core" % "6.0.RC2"
  def specs2Framework = new TestFramework("org.specs2.runner.SpecsFramework")
  override def testFrameworks = super.testFrameworks ++ Seq(specs2Framework)
  override def includeTest(s: String) = { s.endsWith("_spec") }
}

