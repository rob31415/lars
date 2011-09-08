package lards.model.dto

import scala.reflect.BeanProperty
import java.util.Collection
import collection.JavaConversions._


class User(val pid: java.lang.Long = -1,
  @BeanProperty var firstname: String = "", 
  @BeanProperty var lastname: String = "")
  extends Dto(pid) {

  @BeanProperty var role: Role = new Role
  //@TODO: how to make this Dtos without much complexity?
  @BeanProperty var location = new java.util.HashSet[Location]
  
  override def is_same_type(other: lards.model.dto.Dto): Boolean = 
    other.isInstanceOf[User]

  override def toString: String = {
    firstname + ", " + lastname + ", " + id + ", " + super.toString + "; role=" + role.toString
  }
  
}
