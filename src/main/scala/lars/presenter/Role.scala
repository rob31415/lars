/**
*/

package lars.presenter

import lars.global.Applocal
import lars.global.Logger
import lars.presenter.Editwindow
import lars.model.event.{Role => Model_event}
import lars.view.event.{Role => View_event}


class Role(view: lars.view.Role, model: lars.model.service.Role)
  extends Editwindow(view, model, 'role) 
  with Logger {

  override def notify(event: Any) {
    log_debug("presenter.Role got event " + event)

    event match {

      // database was queried
      case event: Model_event => {
        reload
      }

      case _ =>
    }
    
    super.notify(event)
  }


  def comes_from_associated_view(event: lars.view.event.Editwindow): Boolean = {
    event.isInstanceOf[View_event]
  }
  
}
