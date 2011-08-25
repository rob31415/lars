/**
*/

package lards.presenter

import lards.global.Applocal
import lards.presenter.Editwindow
import lards.model.event.{Role => Model_event}


class Role(view: lards.view.Role, model: lards.model.service.Role)
  extends Editwindow(view, model, 'role) {

  override def notify(event: Any) {
    println("presenter.Role got event " + event)

    event match {

      // database was queried
      case event: Model_event => {
        reload
      }

      case _ =>
    }
    
    super.notify(event)
  }
  
}
