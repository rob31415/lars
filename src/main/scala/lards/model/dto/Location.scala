package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: get rid of ugly null. is making Option as id mybatis-compatible with typeHandler somehow?
class Location(val id: String = null) {
  
}
