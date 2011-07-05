package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: get rid of ugly -1. is making Option as id mybatis-compatible with typeHandler somehow?
class User(val id: java.lang.Long = -1, 
  @BeanProperty var firstname: String = "", 
  @BeanProperty var lastname: String = "") {

  def get_roles() : List[Role] = {
    List(new Role())
  }

  def get_locations() : List[Location] = {
    List(new Location())
  }
  
}
