/**
Standard-CRUD

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
history-tab is enabled only if one and only one row is selected in edit-tab.
  it shows all modifications ever made to the record.
  it has same row&col setup than main tab.
  additionally there are two cols more, namely timestamp and modified-by-user
row-filter adjustable and managable pro user
col-setup is adjustable and managable pro user
sorting-setup is adjustable and managable pro user
the window geo, currently selected row-filter, col-setup, sorting and probably row-selection(s) are preserved by window closing and data reloading
*/

package lars.presenter

import lars.global.Applocal
import collection.JavaConversions._
import lars.global.Logger
import lars.model.dto.Dto
import lars.model.dto.Dtos
import lars.view.event.{Editwindow => View_event}
import lars.view.Editwindow
import lars.model.service.Dao
import lars.global.Logger



abstract class Editwindow(view: lars.view.Editwindow, model: Dao, val menu_id: Symbol) 
  extends Logger {

  Applocal.broadcaster.subscribe(this)
  var data: Dtos = _

  log_debug("creating, listening to menu_id " + menu_id)


  def notify(event: Any) {
    log_debug("got event " + event)

    event match {

      // mainmenu-item selected
      
      case event: lars.view.event.Main => {
        log_debug("event-meaning= " + event.meaning)
        
        if(event.meaning == menu_id) {
          if(view.is_shown) {
            view.hide
          } else {
            view.show
            reload
          }
        }
      }

      //something happend in the associated view

      case event: lars.view.event.Editwindow => {

        if(comes_from_associated_view(event)) {
          log_debug("event-meaning=" + event.meaning + " event=" + event + " type=" + event.asInstanceOf[View_event].getClass)

          event.meaning match {
            //selection in table changed
            case 'select => {
              if(event.dtos.get.get.size == 1) {
                val data_edit = data.get.get.find({e => e.id == event.dtos.get.get.iterator.next.id}).get
                val data_history = model.get_history(data_edit.id).get
                view.set_data(data_edit, data_history)
              }
              view.set_number_of_selected_display(data.get.get.size, event.dtos.get.get.size)
            }
            //save button pressed
            case 'save => {
              if(event.dtos.get.get.size == 1) {
                val obj = event.dtos.get.get.iterator.next
                log_debug("saving " + obj)
                model.save(obj)
                view.restore_view_state
              } else {
                log_debug("saving of more than 1 collectively not implemented")
              }
            }
            //delete button pressed
            case 'delete => {
              model.delete(event.dtos)
              view.restore_view_state
            }
            //user intends to edit
            case 'start_modify => {
              if(!lars.global.Editlock.add(view.get_current_edit_data))
                view.lock_edit
            }
            //editing cancelled
            case 'cancel_modify => {
              lars.global.Editlock.remove(view.get_current_edit_data)
            }
            //window is closed by user
            case 'close => {
              view.hide
              lars.global.Editlock.remove(view.get_current_edit_data)
            }

            case _ =>
          }
          
        } //if
        
      } //case


      // app' is going down
      case event: lars.presenter.event.Main => {
        event.meaning match {
          case 'shutdown => {
            view.hide
          }
    
          case _ =>
        }
      }

      case _ =>
    }

    log_debug("processing of event " + event + " finished")
  }
  
  
  def reload {
    log_debug("reload")
    data = model.get_all()
    view.set_data(data) 
  }

  def comes_from_associated_view(event: lars.view.event.Editwindow): Boolean

}

