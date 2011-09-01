/**
*/

package lards.presenter

import lards.global.Applocal
import lards.presenter.Editwindow
import lards.model.event.{User => Model_event}
import lards.view.event.{User => View_event}
import lards.model.dto.{User => Dto}


class User(view: lards.view.User, model: lards.model.service.User, model_role: lards.model.service.Role)
  extends Editwindow(view, model, 'user) {


  override def notify(event: Any) {
    super.notify(event)

    println("presenter.User got event " + event)

    event match {

      case event: Model_event => reload

      // foreign model event
      case event: lards.model.event.Role => reload
      
      // from own-view
      case event: View_event => {
        if(event.meaning == 'select) {
          if(event.dtos.get.get.size == 1) {
            view.set_edit_form_role_selection(event.dtos.get.get.iterator.next.asInstanceOf[Dto])
          }
        }
      }

      case _ =>
    }
    
  }

  
  def comes_from_associated_view(event: lards.view.event.Editwindow): Boolean = {
    event.isInstanceOf[View_event]
  }
  
  
  override def reload {
    super.reload
    view.set_role_data(model_role.get_all)
    view.rebuild
  }

}
