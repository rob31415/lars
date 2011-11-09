package lards.model.dto

import scala.reflect.BeanProperty
import java.sql.Timestamp
import lards.global.Now
import lards.business_process.aspect.Aspect
import lards.business_process.aspect.Aspect_interface


class Transport(val pid: java.lang.Long = -1,
  val ptimestamp: java.sql.Timestamp = Now.timestamp,
  @BeanProperty var description: String = "")
  extends Dto(pid, ptimestamp)
  with Aspect_interface[lards.model.dto.Transport]
  with Tagable {

  //@TODO: how to make this Dtos without much complexity?
  @BeanProperty var tag = new java.util.HashSet[Tag]


  //mybatis wants this
  def this() = this(-1, Now.timestamp, "")

  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Transport]

  override def toString: String = description

  override def get_tags(): java.util.HashSet[lards.model.dto.Tag] = tag
  
  def set_tags(tag: java.util.HashSet[lards.model.dto.Tag]) = this.tag = tag

}
