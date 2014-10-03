
//libraryDependencies <+= sbtVersion(v => v match {
// case "0.11.2" => "com.github.siasia" %% "xsbt-web-plugin" % "0.11.2-0.2.10"
// case "0.11.3" => "com.github.siasia" %% "xsbt-web-plugin" % "0.11.3-0.2.11"
// })
 
seq(webSettings :_*)

libraryDependencies += "org.mortbay.jetty" % "jetty" % "6.1.22" % "container"

// host in container.Configuration := 0.0.0.0

libraryDependencies += "javax.servlet" % "servlet-api" % "2.5"

libraryDependencies += "com.vaadin" % "vaadin" % "6.7.1"

libraryDependencies += "postgresql" % "postgresql" % "9.1-901.jdbc4"

libraryDependencies += "org.mybatis" % "mybatis" % "3.2.1"

libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.6.1"


