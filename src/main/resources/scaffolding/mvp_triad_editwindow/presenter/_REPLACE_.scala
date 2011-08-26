/**
*/

package lards.presenter

import lards.global.Applocal
import lards.presenter.Editwindow
import lards.model.event.{_REPLACE_ => Model_event}


class _REPLACE_(view: lards.view._REPLACE_, model: lards.model.service._REPLACE_)
  extends Editwindow(view, model, '_REPLACE_) {

  override def notify(event: Any) {
    println("presenter._REPLACE_ got event " + event)

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
