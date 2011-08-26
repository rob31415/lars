package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: remove id, make description primary-key
class _REPLACE_(override val id: java.lang.Long = -1, 
             @BeanProperty var _REPLACE_: _REPLACE = _REPLACE_)
  extends Dto {

  
  def toArray(): Array[Object] = {
    var list = new java.util.ArrayList[Object]
    list.add(_REPLACE_)
    list.toArray()
  }

  
  //@TODO: what happens if an object is passed with a type other than _REPLACE_?
  override def equals(that: Any): Boolean = {
    val that_id = that.asInstanceOf[_REPLACE_].id
    println("_REPLACE_ equals that.id=" + that_id + " this.id=" + id)
    that_id == id
  }

}
