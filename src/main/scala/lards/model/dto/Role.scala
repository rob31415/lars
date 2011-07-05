package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: get rid of ugly -1. is making Option as id mybatis-compatible with typeHandler somehow?
class Role(val id: java.lang.Long = -1, 
             @BeanProperty var description: String = "") {

  def get_users() : List[User] = {
    List(new User())
  }

  def get_deeds() : List[Deed] = {
    List(new Deed())
  }
  
}
