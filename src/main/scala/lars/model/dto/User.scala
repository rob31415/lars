package lars.model.dto

import scala.reflect.BeanProperty
import java.util.Collection
import collection.JavaConversions._
import java.sql.Timestamp
import lars.global.Now


class User(val pid: java.lang.Long = -1,
  val ptimestamp: java.sql.Timestamp = Now.timestamp,
  @BeanProperty var firstname: String = "", 
  @BeanProperty var lastname: String = "")
  extends Dto(pid, ptimestamp) {

  @BeanProperty var role: Role = new Role
  //@TODO: how to make this Dtos without much complexity?
  @BeanProperty var location = new java.util.HashSet[Location]


  //mybatis wants this
  def this() = this(-1, Now.timestamp, "", "")
  
  override def is_same_type(other: lars.model.dto.Dto): Boolean = 
    other.isInstanceOf[User]

  override def toString: String = {
    //firstname + ", " + lastname + ", " + id + ", " + super.toString + "; role=" + role.toString
    firstname + " location=" + location
  }
  
}
