/**
*/

package lards.presenter

import lards.global.Applocal
import lards.presenter.Editwindow
import lards.model.event.{_REPLACE_ => Model_event}
import lards.view.event.{_REPLACE_ => View_event}


class _REPLACE_(view: lards.view._REPLACE_, model: lards.model.service._REPLACE_, _REPLACE_)
  extends Editwindow(view, model, '_REPLACE_) {


  override def notify(event: Any) {
    super.notify(event)

    println("presenter._REPLACE_ got event " + event)

    event match {

      case event: Model_event => reload

      //@TODO: remove line if no 1:n is wanted
      // foreign model event
      case event: lards.model.event._REPLACE_ => reload

      //@TODO: remove line if no n:n is wanted
      // foreign model event
      case event: lards.model.event._REPLACE_ => reload

      case _ =>
    }
    
    super.notify(event)
  }

  
  def comes_from_associated_view(event: lards.view.event.Editwindow): Boolean = {
    event.isInstanceOf[View_event]
  }


  override def reload {
    super.reload
    //@TODO: remove line if no 1:n with automatic gui-field-naming 
    // and no n:n is wanted.
    // or alter arguments accordingly otherwise.
    view.create_form_field_factory(_REPLACE_.get_all, _REPLACE_.get_all)
    view.rebuild
  }

}
