/**
*/

package lards.model.dto


import scala.reflect.BeanProperty
import java.sql.Timestamp
import lards.global.Now


class Aspect(val pid: java.lang.Long = -1, 
  @BeanProperty var description: String = "",
  @BeanProperty var tag_imperative: java.util.HashSet[Tag] = new java.util.HashSet[Tag],
  @BeanProperty var tag_prohibitive: java.util.HashSet[Tag] = new java.util.HashSet[Tag]
  )
  extends Dto(pid, Now.timestamp /*@TODO!*/) {


  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Aspect]

}
