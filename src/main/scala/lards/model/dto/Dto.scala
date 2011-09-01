package lards.model.dto

abstract class Dto(var id: java.lang.Long = -1) {


  // returns whether the given type the same as the type of the class overriding this
  def is_same_type(other: lards.model.dto.Dto): Boolean


  def equals(other: lards.model.dto.Dto): Boolean = {
    var retVal: Boolean = false

    if(is_same_type(other)) {
      retVal = other.id == id
    } else {
      retVal = false
    }

    if(retVal)
      println("Dto.equals: Dto(id=" + id + ") equals other (id=" + other.id + ")")
    else
      println("Dto.equals: Dto(id=" + id + ") doesn't equal other (id=" + other.id + ")")

    retVal
  }
  
/* do we need this? sideeffects?
 
  override def hashCode(): Int = {
    val prime = 31
    val result = super.hashCode
    val add = if(id == -1) id.hashCode else 0
    val hash = prime * result + add
    println("Dto.hashCode="+hash)
    hash
  }
*/

}
