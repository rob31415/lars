/**
*/

package lards.presenter

import lards.global.Applocal
import lards.presenter.Editwindow
import lards.model.event.{Location => Model_event}
import lards.view.event.{Location => View_event}


class Location(view: lards.view.Location, model: lards.model.service.Location)
  extends Editwindow(view, model, 'location) {

  override def notify(event: Any) {
    println("presenter.Location got event " + event)

    event match {

      // database was queried
      case event: Model_event => {
        println("presenter.Location reloading")
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
