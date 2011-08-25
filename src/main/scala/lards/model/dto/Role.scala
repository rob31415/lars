package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: remove id, make description primary-key
class Role(override val id: java.lang.Long = -1, 
             @BeanProperty var description: String = "")
  extends Dto {

  
  def toArray(): Array[Object] = {
    var list = new java.util.ArrayList[Object]
    list.add(description)
    list.toArray()
  }

  
  //@TODO: what happens if an object is passed with a type other than Role?
  override def equals(that: Any): Boolean = {
    val that_id = that.asInstanceOf[Role].id
    println("Role equals that.id=" + that_id + " this.id=" + id)
    that_id == id
  }

}
