/**
Role CRUD.

multiple selection possible
new-tab is always enabled
  once entered data stays unless create-button is clicked
  once there is unsaved data, there is a user-notification when navigating to another tab or when closing the window
edit-tab is enabled only if one and only one row is selected
  once there is modified and positively validated data, there is a user-notification (warning of data-loss) when navigating to another tab or when closing the window
  if another user has started to edit the record, the user is notified about the other user-name and edit ui-elements become locked
    once the other user has saved the data, the edit form is updated and edit-lock disappears
delete-tab is always enabled, has a summary of currently selected data and a yes/no dialog when clicking delete-button
  records in use by other users are regarded in the summary
row-filter adjustable and managable pro user
col-setup is adjustable and managable pro user
sorting-setup is adjustable and managable pro user
the window geo, currently selected row-filter, col-setup, sorting and probably row-selection(s) are preserved by window closing and data reloading
*/

package lards.presenter

import lards.global.Applocal
import lards.view.Login
import lards.model.Login


class Role(view: lards.view.Role, model: lards.model.service.Role) {

  Applocal.broadcaster.subscribe(this)
  var data: Seq[lards.model.dto.Role] = _
  var currently_edited_dto: Option[lards.model.dto.Role] = None


  def notify(event: Any) {
    println("role-presenter got event " + event)

    event match {

      // mainmenu-item selected
      case event: lards.view.event.Main => {
        event.meaning match {
          case 'roles => {
            if(view.is_shown) {
              view.hide
            } else {
              view.show
              data = model.get_all.get
              view.set_data(data)
            }
          }
      
          case _ =>
        }
      }

      //something happend in the associated view
      case event: lards.view.event.Role => {
        event.meaning match {
          //selection in table changed
          case 'select => {
            if(event.dto_list.get.size == 1) {
              view.set_data(data.find({e => e.id == event.dto_list.get.iterator.next.id}).get)
            }
            view.set_number_of_selected_display(data.length, event.dto_list.get.size)
          }
          //save button pressed
          case 'save => {
            if(event.dto_list.size == 1) {
              val obj = event.dto_list.get.iterator.next
              println("saving " + obj)
              model.save(obj)
            } else {
              println("saving of more than 1 collectively not implemented")
            }
          }
          //delete button pressed
          case 'delete => {
            model.delete(event.dto_list.get)
          }
          //user intends to edit
          case 'start_modify => {
            if(currently_edited_dto != None)
              unlock_dto(currently_edited_dto.get)
            currently_edited_dto = view.get_current_edit_data()
            if(currently_edited_dto != None)
              lock_dto(currently_edited_dto.get)
          }
          //editing cancelled
          case 'cancel_modify => {
            if(currently_edited_dto != None)
              unlock_dto(currently_edited_dto.get)
            currently_edited_dto = None
          }
          //window is closed by user
          case 'close => {
            view.hide
            if(currently_edited_dto != None)
              unlock_dto(currently_edited_dto.get)
          }

          case _ =>
        }
      }

      // database was queried
      case event: lards.model.event.Role => {
        data = model.get_all.get
        view.set_data(data) 
      }

      // app' is going down
      case event: lards.presenter.event.Main => {
        event.meaning match {
          case 'shutdown => {
            view.hide
          }
      
          case _ =>
        }
      }

      case _ =>
    }
  }


  def unlock_dto(dto: lards.model.dto.Role) {
    lards.global.Editlock.remove(dto)
    currently_edited_dto = None
  }


  def lock_dto(dto: lards.model.dto.Role) {
    lards.global.Editlock.add(dto)
    currently_edited_dto = Some(dto)
  }

}