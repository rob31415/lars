/**
implements is_shown notion.
useful e.g. if a presenter manages more than one view.
*/
package lards.view
import lards.global.Logger


trait View extends Logger {

  private var shown: Boolean = false


  final def show {
    if (!shown) {
      log_debug("showing view")
      on_show
    }
    shown = true
  }

  final def hide {
    if (shown) {
      log_debug("hiding view")
      on_hide
    }
    shown = false
  }
  
  def is_shown = shown

  def on_show

  def on_hide

  def create_elements
  
  //@TODO: can it be made so, that this is called *after* 
  //       the derived class' constructor?
  //create_elements
}
