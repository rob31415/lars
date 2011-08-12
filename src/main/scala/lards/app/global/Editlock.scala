/**
This maintains a set of dtos which are currently being edited by any user.
a dto goes in the set when a user starts to edit a record.
the dto is removed when the user
-saves the record
-gets out of edit-mode
-closes window
-logs out
-timeout !?
-garbage collection !?

responsibility of adding and removing the dtos here lies within the presenters.

@TODO:
-send message on remove and add so others can react by enabling editing (first come first served style)
-also add user to be able to display who has currently locked the record
*/

package lards.global


object Editlock {

  var dtos = Set[Any]()

  def add(dto: Any) {
    println("Editlock add " + dto)
    dtos += dto
  }
  
  
  def remove(dto: Any) {
    println("Editlock remove " + dto)
    dtos -= dto
  }
  
  
  def contains(dto: Any): Boolean = {
    val ret_val = !dtos.find(o => o == dto).isEmpty
    println("Editlock contains " + dto + " = " + ret_val)
    ret_val
  }

}
