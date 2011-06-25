package lards.view

trait View extends  {

  private var shown: Boolean = false


  final def show {
    if (!shown) on_show
    shown = true
  }

  final def hide {
    if (shown) on_hide
    shown = false
  }
  
  def is_shown = shown

  def on_show

  def on_hide

  def init

}
