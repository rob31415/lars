/**
this is used as a helper and not stored/retrieved from database "as-is".
it contains a timestamp.
*/

package lars.model.dto

import scala.reflect.BeanProperty
import java.sql.Timestamp
import lars.global.Now


class Timestamp(val pid: java.lang.Long = -1,
  val ptimestamp: java.sql.Timestamp)
  extends Dto(-1, ptimestamp) {


  //mybatis wants this
  def this() = this(-1, Now.timestamp)
    

  override def is_same_type(other: lars.model.dto.Dto): Boolean = 
    other.isInstanceOf[Timestamp]


  def toArray(): Array[Object] = {
    var list = new java.util.ArrayList[Object]
    list.add(timestamp)
    list.toArray()
  }

  
  override def toString(): String = timestamp.toString // + "(" + id + ")" + super.toString

}
