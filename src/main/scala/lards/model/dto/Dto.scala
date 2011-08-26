package lards.model.dto

abstract class Dto(val id: java.lang.Long = -1) {


  // returns whether the given type the same as the type of the class overriding this
  def is_same_type(other: lards.model.dto.Dto): Boolean


  def equals(other: lards.model.dto.Dto): Boolean = {
    if(is_same_type(other)) {
      val that_id = other.id
//      println("Role equals other.id=" + that_id + " this.id=" + id)
      that_id == id
    } else {
      false
    }
  }

}
