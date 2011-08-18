/**
application menu, status lines and background-picture
*/

package lards.view

import event._
import com.vaadin.ui._
import event.Main
import lards.global.Applocal


/**
@emits lards.view.event.Main
*/
class Main(var window: Window) extends Panel with View {

  class Menu_Command(val meaning: Symbol) extends MenuBar.Command {
    def menuSelected(selectedItem: MenuBar#MenuItem) {
      //label_info.setValue("Menu selection=" + selectedItem.getText())
      Applocal.broadcaster.publish(new lards.view.event.Main(meaning))
    }
  }
  
  val user_info: Label = new Label("unknown user")

  create_elements
  

  override def create_elements {
    addComponent(user_info)
    addComponent(create_menu())
    setSizeFull()

    println("main_view created")
  }


  def create_menu(): MenuBar = {
    val menu_bar = new MenuBar()
    create_menu1(menu_bar)
    create_menu2(menu_bar)
  }


  def create_menu1(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Programm", null, null)

    menu.addItem("Abmelden", new Menu_Command('logout))
    menu.addItem("Über Lars", new Menu_Command('about))

    menu_bar
  }


  def create_menu2(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Einstellungen", null, null)

    menu.addItem("Rollen", new Menu_Command('roles))
    menu.addItem("Benutzer", new Menu_Command('user))
    menu.addItem("Locations", new Menu_Command('locations))

    menu_bar
  }


  override def on_show = {
    window.setContent(this)
  }


  override def on_hide = {
    window.setContent(null)
  }

}
