package lars.model.dto

import scala.reflect.BeanProperty


//@TODO: get rid of ugly null. is making Option as id mybatis-compatible with typeHandler somehow?
//@TODO: Option for done would be good
class Deed(val id: String = null,
           @BeanProperty var description: String = "",
           @BeanProperty var done: Boolean = false) {
  
  def get_roles() : List[Role] = {
    List(new Role())
  }
  
}
