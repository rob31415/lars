/**
*/

package lars.model.dto

import scala.reflect.BeanProperty
import java.sql.Timestamp
import lars.global.Now


class Tag(val pid: java.lang.Long = -1,
  @BeanProperty var mnemonic: Symbol = 'undefined,
  @BeanProperty var description: String = "")
  extends Dto(pid, Now.default_time) {

  override def is_same_type(other: lars.model.dto.Dto): Boolean = 
    other.isInstanceOf[Tag]

}
