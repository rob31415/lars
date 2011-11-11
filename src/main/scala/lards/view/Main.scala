/**
application menu, status lines and background-picture
*/

package lards.view

import event._
import com.vaadin.ui._
import event.Main
import lards.global.Applocal
import lards.global.Logger


/**
@emits lards.view.event.Main
*/
class Main(var parent: Window) extends Panel with View with Logger {

  class Menu_Command(val meaning: Symbol) extends MenuBar.Command {
    def menuSelected(selectedItem: MenuBar#MenuItem) {
      //label_info.setValue("Menu selection=" + selectedItem.getText())
      Applocal.broadcaster.publish(new lards.view.event.Main(meaning))
    }
  }
  
  val user_info: Label = new Label("unknown user")

  create_elements
  

  override def create_elements {
    log_debug("create_elements(): GEO=" + parent.getWidth() + ", " + parent.getHeight())

    addComponent(user_info)
    addComponent(create_menu())
    setSizeFull()

    log_debug("create_elements() finished")
  }


  def create_menu(): MenuBar = {
    val menu_bar = new MenuBar()
    create_menu_programm(menu_bar)
    create_menu_erfassungen(menu_bar)
    create_menu_verarbeitungen(menu_bar)
    create_menu_auswertungen(menu_bar)
    create_menu_einstellungen(menu_bar)
    //create_menu_systemeinstellungen(menu_bar)
  }


  def create_menu_programm(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Programm", null, null)

    menu.addItem("Abmelden", new Menu_Command('logout))
    menu.addItem("Über Lars", new Menu_Command('about))

    menu_bar
  }


  def create_menu_erfassungen(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Erfassungen/Auskunft", null, null)

    menu.addItem("Transporberichte", new Menu_Command('transport))
    menu.addItem("Orte", new Menu_Command('location))
    menu.addItem("Teilnehmer", new Menu_Command('participant))
    menu.addItem("Rechnungsposten", new Menu_Command('item))

    menu_bar
  }


  def create_menu_verarbeitungen(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Verarbeitungen", null, null)

    menu.addItem("Dekadenverarbeitung", new Menu_Command('dekadenverarbeitung))

    menu_bar
  }


  def create_menu_auswertungen(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Auswertungen", null, null)

    menu.addItem("Soll-Liste der aktuellen Dekade", new Menu_Command('logout))
    menu.addItem("Überweisungsliste der aktuellen Dekade", new Menu_Command('about))
    menu.addItem("Giroliste", new Menu_Command('about))

    menu_bar
  }

  def create_menu_einstellungen(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Einstellungen", null, null)

    menu.addItem("Kostenträger", new Menu_Command('todo))
    menu.addItem("Tarife", new Menu_Command('tariff))
    menu.addItem("Konten", new Menu_Command('account))
    menu.addItem("Umlagen", new Menu_Command('todo))
    menu.addItem("Fahrzeuge", new Menu_Command('vehicle))
    var submenu1: MenuBar#MenuItem = menu.addItem("Beauftragte", null, null)
      submenu1.addItem("Beauftragte", new Menu_Command('todo))
      submenu1.addItem("Beauftragte-Typen", new Menu_Command('todo))
    menu.addItem("Einsatzarten", new Menu_Command('todo))
    var submenu2: MenuBar#MenuItem = menu.addItem("Benutzer", null, null)
      submenu2.addItem("Benutzer", new Menu_Command('user))
      submenu2.addItem("Angestelltenverhältnisse", new Menu_Command('todo))
      submenu2.addItem("Qualifikationen", new Menu_Command('todo))
    var submenu3: MenuBar#MenuItem = menu.addItem("Systemeinstellungen", null)
      submenu3.addItem("Systemzeit", new Menu_Command('sys_time))

    menu_bar
  }


  override def on_show {
    log_debug("on_show(): GEO=" + parent.getWidth() + ", " + parent.getHeight())
    parent.setContent(this)
  }


  override def on_hide {
    parent.setContent(null)
  }

}
