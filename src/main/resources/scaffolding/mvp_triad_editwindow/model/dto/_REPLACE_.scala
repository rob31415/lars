package lards.model.dto

import scala.reflect.BeanProperty
//@TODO: remove next line if no 1:n or 1:n is wanted
import java.util.Collection
import collection.JavaConversions._


class _REPLACE_(override val id: java.lang.Long = -1, 
             @BeanProperty var _REPLACE_: _REPLACE = _REPLACE_)
  extends Dto {

  //@TODO: remove next line if no 1:n is wanted
  @BeanProperty var _REPLACE_: _REPLACE_ = new _REPLACE_
  //@TODO: how to make this Dtos without much complexity?
  //@TODO: remove next line if no n:n is wanted
  @BeanProperty var _REPLACE_ = new java.util.HashSet[_REPLACE_]


  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[_REPLACE_]

}
