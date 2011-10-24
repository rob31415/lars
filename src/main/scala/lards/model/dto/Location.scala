package lards.model.dto

import scala.reflect.BeanProperty
import java.sql.Timestamp
import lards.global.Now


class Location(val pid: java.lang.Long = -1,
  val ptimestamp: java.sql.Timestamp = Now.timestamp,
  @BeanProperty var description: String = "")
  extends Dto(pid, ptimestamp) {

  @BeanProperty var user = new java.util.HashSet[User]


  //mybatis wants this
  def this() = this(-1, Now.timestamp, "")

  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Location]

  override def toString: String = description //+ " USER=" + user

}
