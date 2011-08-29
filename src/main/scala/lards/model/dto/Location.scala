package lards.model.dto

import scala.reflect.BeanProperty


class Location(val pid: java.lang.Long = -1, 
             @BeanProperty var description: String = "")
  extends Dto(pid) {

  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Location]

}
