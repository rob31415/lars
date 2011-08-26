package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: remove id, make description primary-key
class _REPLACE_(override val id: java.lang.Long = -1, 
             @BeanProperty var _REPLACE_: _REPLACE = _REPLACE_)
  extends Dto {


  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[_REPLACE_]

}
