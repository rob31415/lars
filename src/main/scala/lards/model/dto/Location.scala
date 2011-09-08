package lards.model.dto

import scala.reflect.BeanProperty


class Location(val pid: java.lang.Long = -1, 
             @BeanProperty var description: String = "")
  extends Dto(pid) {


  //mybatis wants this
  def this() = this(-1,"")


  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Location]

}
