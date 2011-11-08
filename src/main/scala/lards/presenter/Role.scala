/**
*/

package lards.presenter

import lards.global.Applocal
import lards.global.Logger
import lards.presenter.Editwindow
import lards.model.event.{Role => Model_event}
import lards.view.event.{Role => View_event}


class Role(view: lards.view.Role, model: lards.model.service.Role)
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


  def comes_from_associated_view(event: lards.view.event.Editwindow): Boolean = {
    event.isInstanceOf[View_event]
  }
  
}
