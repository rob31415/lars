package lards.model.dto

import java.sql.Timestamp


abstract class Dto(var id: java.lang.Long = -1,
  var timestamp: Timestamp = new Timestamp(0)) {


  // returns whether the given type the same as the type of the class overriding this
  def is_same_type(other: lards.model.dto.Dto): Boolean

  
  override def equals(other: Any /*lards.model.dto.Dto*/ ): Boolean = {
    var retVal: Boolean = false

    if(other.isInstanceOf[lards.model.dto.Dto])
    {
      val other_casted = other.asInstanceOf[lards.model.dto.Dto]
      if(is_same_type(other_casted)) {
        retVal = other_casted.id == id
      } else {
        retVal = false
      }
    } else {
      false
    }

/*
    if(retVal)
      println("Dto.equals: Dto(id=" + id + ") equals other")
    else
      println("Dto.equals: Dto(id=" + id + ") doesn't equal other")
*/

    retVal
  }
  

  //@TODO: sideeffects? what's the proper way for generation?
 
  override def hashCode(): Int = {
    val hash = 41 * id.hashCode
    //println("Dto.hashCode="+hash)
    hash
  }


}
