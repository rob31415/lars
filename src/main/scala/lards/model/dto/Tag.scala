/**
*/

package lards.model.dto

import scala.reflect.BeanProperty
import java.sql.Timestamp
import lards.global.Now


class Tag(val pid: java.lang.Long = -1,
  @BeanProperty var mnemonic: Symbol = 'undefined,
  @BeanProperty var description: String = "")
  extends Dto(pid, Now.timestamp /*@TODO!*/) {

  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[Tag]

}
