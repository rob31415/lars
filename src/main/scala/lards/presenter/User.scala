/**
*/

package lards.presenter

import lards.global.Applocal
import lards.presenter.Editwindow
import lards.model.event.{User => Model_event}
import lards.view.event.{User => View_event}


class User(view: lards.view.User, model: lards.model.service.User)
  extends Editwindow(view, model, 'user) {

  override def notify(event: Any) {
    println("presenter.User got event " + event)

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
