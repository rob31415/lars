/**
this maintains a set of dtos which are currently being 
edited by a certain user.

another user can't remove an entry.

a dto goes in the set when a user starts to edit a record.

the dto is removed when the user either:
-saves the record
-gets out of edit-mode
-closes window
-logs out

responsibility of adding and removing the dtos here lies within the presenters.

@TODO:
-how to avoid infinite-locks? timeout !?
-is there a maybe garbage collection problem lurking !?
-add possibility to get who has currently locked the record
-send message on remove and add so others can react by enabling editing (first come first served style)
*/

package lars.global

import lars.global.Logger


object Editlock
  extends Logger {

  var dtos = Set[(lars.model.dto.Dto, lars.model.dto.User)]()


  //adding is successful, if option is defined and element not already in set
  def add(dto: Option[lars.model.dto.Dto]): Boolean = {
    if(dto.isDefined && !contains(dto)) {
      log_debug("add(" + dto.get + ")")
      val set_entry = (dto.get, Applocal.get_user)
      dtos += set_entry
      return true
    }
    false
  }


  /*
  removing is successful, if option is defined and element is in set 
  and removing user is the one who added
  */
  def remove(dto: Option[lars.model.dto.Dto]): Boolean = {
    val set_entry = dtos.find(o => o._1 == dto.get).getOrElse((null, null))
    if(contains(dto) && set_entry._2 == Applocal.get_user) {
      log_debug("remove(" + dto.get + ")")
      dtos -= set_entry
      return true
    }
    false
  }
  

  //true if option is defined and element is in set
  def contains(dto: Option[lars.model.dto.Dto]): Boolean = {
    val ret_val = dto.isDefined && dtos.find(o => o._1 == dto.get).isDefined
    log_debug("contains(" + dto.getOrElse("nothing") + ") = " + ret_val)
    ret_val
  }

}
