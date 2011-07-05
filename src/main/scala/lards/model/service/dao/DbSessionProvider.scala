package lards.model.service

import java.util.logging.Logger
import org.apache.ibatis.session._ //{SqlSessionFactoryBuilder,SqlSession}
import org.apache.ibatis.io._ //{SqlSessionFactoryBuilder,SqlSession}


object DbSessionProvider {
  private val resource: String = "mybatis/config.xml"
  private val reader = Resources.getResourceAsReader(resource)
  private val builder: SqlSessionFactoryBuilder = new SqlSessionFactoryBuilder()
  val factory: SqlSessionFactory = builder.build(reader)
}
