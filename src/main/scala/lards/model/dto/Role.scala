package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: remove id, make description primary-key
class Role(val id: java.lang.Long = -1, 
             @BeanProperty var description: String = "") {
  
  def toArray(): Array[Object] = {
    var list = new java.util.ArrayList[Object]
    list.add(description)
    list.toArray()
  }
}
