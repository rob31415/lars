/**
*/

package lards.presenter

import lards.global.Applocal
import lards.presenter.Editwindow
import lards.model.event.{User => Model_event}
import lards.view.event.{User => View_event}
import lards.model.dto.{User => Dto}


class User(view: lards.view.User, model: lards.model.service.User, model_role: lards.model.service.Role, model_location: lards.model.service.Location)
  extends Editwindow(view, model, 'user) {


  override def notify(event: Any) {
    super.notify(event)

    println("presenter.User got event " + event)

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
    view.create_form_field_factory(model_role.get_all, model_location.get_all)
    view.rebuild
  }

}
