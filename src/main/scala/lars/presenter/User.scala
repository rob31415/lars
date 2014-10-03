/**
*/

package lars.presenter

import lars.global.Applocal
import lars.presenter.Editwindow
import lars.model.event.{User => Model_event}
import lars.view.event.{User => View_event}
import lars.global.Logger
import lars.global.Protocolar
import lars.global.Now


class User(view: lars.view.User, model: lars.model.service.User, model_role: lars.model.service.Role, model_location: lars.model.service.Location)
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
      case event: lars.model.event.Role => reload

      // foreign model event
      case event: lars.model.event.Location => {
        event.meaning match {
          case 'undefined => reload
          case _ =>
        }
      }

      case _ =>
    }
    
  }

  
  def comes_from_associated_view(event: lars.view.event.Editwindow): Boolean = {
    event.isInstanceOf[View_event]
  }
  
  
  override def reload {
    super.reload
    view.create_form_field_factory(model_role.get_all(), model_location.get_all())
    view.rebuild
  }

}
