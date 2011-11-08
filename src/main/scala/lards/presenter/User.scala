/**
*/

package lards.presenter

import lards.global.Applocal
import lards.presenter.Editwindow
import lards.model.event.{User => Model_event}
import lards.view.event.{User => View_event}
import lards.global.Logger
import lards.global.Protocolar
import lards.global.Now


class User(view: lards.view.User, model: lards.model.service.User, model_role: lards.model.service.Role, model_location: lards.model.service.Location)
  extends Editwindow(view, model, 'user)
  with Logger
  with Protocolar {

  create_protocolar("protocol.test")


  override def notify(event: Any) {
    super.notify(event)

    log_debug("notify(" + event + ")")

    protocol("")
    protocol("Protokoll vom: " + Now.timestamp + " (time override active=" + Now.override_active + ")")
    protocol("Dies ist ein Protokoll-Test")
    protocol("Rechnen wir mal etwas zusammen:")
    protocol("Summe = 42")
    protocol("Protokoll Ende.")
    

    event match {

      case event: Model_event => reload

      // foreign model event
      case event: lards.model.event.Role => reload

      // foreign model event
      case event: lards.model.event.Location => reload

      case _ =>
    }
    
  }

  
  def comes_from_associated_view(event: lards.view.event.Editwindow): Boolean = {
    event.isInstanceOf[View_event]
  }
  
  
  override def reload {
    super.reload
    view.create_form_field_factory(model_role.get_all(), model_location.get_all())
    view.rebuild
  }

}
