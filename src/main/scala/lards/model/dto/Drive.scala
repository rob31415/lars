package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: get rid of ugly -1. is making Option as id mybatis-compatible with typeHandler somehow?
class Drive(val id: java.lang.Long = -1) {

  def get_user() : User = {
    new User()
  }

  def get_location() : Location = {
    new Location()
  }

  def get_tags() : List[Tag] = {
    List(new Tag())
  }

}
