package lards.model.dto

import scala.reflect.BeanProperty
import java.sql.Timestamp
import lards.global.Now


class Transport(val pid: java.lang.Long = -1,
  val ptimestamp: java.sql.Timestamp = Now.timestamp,
  @BeanProperty var description: String = "")
  extends Dto(pid, ptimestamp) {

  //@TODO: how to make this Dtos without much complexity?
  @BeanProperty var tag = new java.util.HashSet[Tag]


  //mybatis wants this
  def this() = this(-1, Now.timestamp, "")

  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Transport]

  override def toString: String = description

}
