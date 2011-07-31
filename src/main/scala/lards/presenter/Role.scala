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
  ?how?
col-setup is adjustable and managable pro user
  ?how?
the window geo, currently selected row-filter, col-setup and row-selection(s) are preserved by window closing and data reloading
*/

package lards.presenter

import lards.global.Applocal
import lards.view.Login
import lards.model.Login


class Role(view: lards.view.Role, model: lards.model.service.Role) {

  Applocal.broadcaster.subscribe(this)
  var data: Seq[lards.model.dto.Role] = _


  def notify(event: Any) {
    println("role-presenter got event " + event)

    event match {
      case event: lards.view.event.Main => {
        event.meaning match {
          case 'roles => {
            view.show
            data = model.get_all.get
            view.set_data(data) 
          }
      
          case _ =>
        }
      }
      case event: lards.view.event.Role => {
        event.meaning match {
          case 'select => {
            view.set_data(data.find({e => e.id == event.dto.get.id}).get)
          }
          case 'save => {
            println("saving " + event.dto.get)
            model.save(event.dto.get)
          }
      
          case _ =>
        }
      }
      case event: lards.model.event.Change => {
        event.meaning match {
          case 'role => {
            data = model.get_all.get
            view.set_data(data) 
          }
      
          case _ =>
        }
      }
      case _ =>
    }
  }

}