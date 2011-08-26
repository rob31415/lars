package lards.model.dto

import scala.reflect.BeanProperty


class Role(override val id: java.lang.Long = -1, 
             @BeanProperty var description: String = "")
  extends Dto {


  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Role]


  def toArray(): Array[Object] = {
    var list = new java.util.ArrayList[Object]
    list.add(description)
    list.toArray()
  }

}
