/**
*/

package lars.presenter

import lars.global.Applocal
import lars.presenter.Editwindow
import lars.model.event.{Location => Model_event}
import lars.view.event.{Location => View_event}
import lars.global.Logger


class Location(view: lars.view.Location, model: lars.model.service.Location)
  extends Editwindow(view, model, 'location)
  with Logger {

  override def notify(event: Any) {
    log_debug("notify(" + event + ")")

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
