package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: get rid of ugly -1. is making Option as id mybatis-compatible with typeHandler somehow?
class Tag(val id: java.lang.Long = -1, 
             @BeanProperty var description: String = "") {

  //@TODO: is this neccessary?
  def get_drives() : List[Drive] = {
    List(new Drive())
  }
  
}
