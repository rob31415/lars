package lars.model.dto

import scala.reflect.BeanProperty
import java.sql.Timestamp
import lars.global.Now


class Role(val pid: java.lang.Long = -1, 
  val ptimestamp: java.sql.Timestamp = Now.timestamp,
  @BeanProperty var description: String = "")
  extends Dto(pid, ptimestamp) {


  //mybatis wants this
  def this() = this(-1, Now.timestamp, "")
    

  override def is_same_type(other: lars.model.dto.Dto): Boolean = 
    other.isInstanceOf[Role]


  def toArray(): Array[Object] = {
    var list = new java.util.ArrayList[Object]
    list.add(description)
    list.toArray()
  }

  
  override def toString(): String = description // + "(" + id + ")" + super.toString

}
