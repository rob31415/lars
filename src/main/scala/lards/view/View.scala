/**
implements is_shown notion.
useful e.g. if a presenter manages more than one view.
*/
package lards.view


trait View {

  private var shown: Boolean = false


  final def show {
    if (!shown) {
      println("showing view")
      on_show
    }
    shown = true
  }

  final def hide {
    if (shown) {
      println("hiding view")
      on_hide
    }
    shown = false
  }
  
  def is_shown = shown

  def on_show

  def on_hide

  def init

}
