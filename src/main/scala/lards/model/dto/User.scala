package lards.model.dto

import scala.reflect.BeanProperty


class User(val pid: java.lang.Long = -1,
  @BeanProperty var firstname: String = "", 
  @BeanProperty var lastname: String = "")
  extends Dto(pid) {

  @BeanProperty var role: Role = new Role
  
  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[User]

}
