package lards.model.dto

import scala.reflect.BeanProperty


class Location(override val id: java.lang.Long = -1, 
             @BeanProperty var description: String = "")
  extends Dto {

  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Location]

}
