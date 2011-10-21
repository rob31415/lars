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
class Main(var parent: Window) extends Panel with View {

  class Menu_Command(val meaning: Symbol) extends MenuBar.Command {
    def menuSelected(selectedItem: MenuBar#MenuItem) {
      //label_info.setValue("Menu selection=" + selectedItem.getText())
      Applocal.broadcaster.publish(new lards.view.event.Main(meaning))
    }
  }
  
  val user_info: Label = new Label("unknown user")

  create_elements
  

  override def create_elements {
    println("WINDOW MAIN C GEO=" + parent.getWidth() + ", " + parent.getHeight())

    addComponent(user_info)
    addComponent(create_menu())
    setSizeFull()

    println("main_view created")
  }


  def create_menu(): MenuBar = {
    val menu_bar = new MenuBar()
    create_menu1(menu_bar)
    create_menu2(menu_bar)
    create_menu3(menu_bar)
    create_menu4(menu_bar)
    create_menu5(menu_bar)
  }


  def create_menu1(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Programm", null, null)

    menu.addItem("Abmelden", new Menu_Command('logout))
    menu.addItem("Über Lars", new Menu_Command('about))

    menu_bar
  }


  def create_menu2(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Erfassungen", null, null)

    menu.addItem("Transporberichte", new Menu_Command('transport))
    menu.addItem("Orte", new Menu_Command('location))
    menu.addItem("Teilnehmer", new Menu_Command('participant))
    menu.addItem("Rechnungsposten", new Menu_Command('item))

    menu_bar
  }


  def create_menu3(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Auswertungen", null, null)

    menu.addItem("Soll-Liste der aktuellen Dekade", new Menu_Command('logout))
    menu.addItem("Überweisungsliste der aktuellen Dekade", new Menu_Command('about))
    menu.addItem("Giroliste", new Menu_Command('about))

    menu_bar
  }

  def create_menu4(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Einstellungen", null, null)

    menu.addItem("Kostenträger", new Menu_Command('todo))
    menu.addItem("Tarife", new Menu_Command('tariff))
    menu.addItem("Konten", new Menu_Command('account))
    menu.addItem("Umlagen", new Menu_Command('todo))
    menu.addItem("Fahrzeuge", new Menu_Command('vehicle))

    menu_bar
  }


  def create_menu5(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Systemeinstellungen", null, null)

    var submenu1: MenuBar#MenuItem = menu.addItem("Beauftragte", new Menu_Command('todo))
      submenu1.addItem("Beauftragte-Typen", new Menu_Command('todo))
    menu.addItem("Einsatzarten", new Menu_Command('todo))
    var submenu2: MenuBar#MenuItem = menu.addItem("Benutzer", null, new Menu_Command('user))
      submenu2.addItem("Angestelltenverhältnisse", new Menu_Command('todo))
      submenu2.addItem("Qualifikationen", new Menu_Command('todo))
    menu.addItem("Rollen", new Menu_Command('role))

    menu_bar
  }


  override def on_show {
    println("WINDOW MAIN S GEO=" + parent.getWidth() + ", " + parent.getHeight())
    parent.setContent(this)
  }


  override def on_hide {
    parent.setContent(null)
  }

}
