package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: get rid of ugly -1. is making Option as id mybatis-compatible with typeHandler somehow?
class Booking(val id: java.lang.Long = -1) {

  def get_drive() : Drive = {
    new Drive()
  }
  
}
