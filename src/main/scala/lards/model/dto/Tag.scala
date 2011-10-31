/**
*/

package lards.model.dto

import scala.reflect.BeanProperty
import java.sql.Timestamp
import lards.global.Now


object Tag {
  val get_all = new scala.collection.mutable.HashSet[Tag]
  get_all += new Tag(1, Now.timestamp, "nicht gesetzt")
  get_all += new Tag(2, Now.timestamp, "ist geprüft")
  get_all += new Tag(3, Now.timestamp, "rechnung ist erstellt")
}


class Tag(val pid: java.lang.Long = -1, 
  val ptimestamp: java.sql.Timestamp = Now.timestamp,
  @BeanProperty var description: String = "")
  extends Dto(pid, ptimestamp) {

  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Tag]

}
