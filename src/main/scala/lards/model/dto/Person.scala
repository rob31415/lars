package lards.model.dto

import scala.reflect.BeanProperty


//@TODO: get rid of ugly -1. is making Option as id mybatis-compatible with typeHandler somehow?
class Person(val id: java.lang.Long = -1, 
             @BeanProperty var mainname: String = "", 
             @BeanProperty var surname: String = "",
             @BeanProperty var phonenr: String = "") {


  def toArray(): Array[Object] = {
    var list = new java.util.ArrayList[Object]
    list.add(mainname)
    list.add(surname)
    list.add(phonenr)
    list.toArray()
  }

  override def toString(): String = {
    "Person=[" + id + "," + mainname + "," + surname + "," + phonenr + "]"
  }
  
  def clear {
    mainname = ""
    surname = ""
    phonenr = ""
  }
  
}

/*
object Person extends Dto(classOf[Person]) {
}
*/

