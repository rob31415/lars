package lards.view

import event._
import com.vaadin.ui._
//import lards.global.Broadcaster
import event.Main
import lards.global.Applocal


class Main extends Panel with View {

  class Menu_Command(val meaning: Symbol) extends MenuBar.Command {
    def menuSelected(selectedItem: MenuBar#MenuItem) {
      //label_info.setValue("Menu selection=" + selectedItem.getText())
      Applocal.broadcaster.publish(new lards.view.event.Main(meaning))
    }
  }
  
  val user_info: Label = new Label("unknown user")


  init
  

  override def init {
    //setContent(new VerticalLayout());
    addComponent(user_info)
    addComponent(create_menu())
    setSizeFull()

    println("main_view created")
  }


  def create_menu(): MenuBar = {

    var default_command = new MenuBar.Command() {
      def menuSelected(selectedItem: MenuBar#MenuItem) {
        Applocal.broadcaster.publish(new lards.view.event.Main('todo))
      }
    }

    val menu_bar = new MenuBar()

    create_menu1(menu_bar)
    create_menu2(menu_bar)
  }


  def create_menu1(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Datei", null, null)

    menu.addItem("Logout", new Menu_Command('logout))

    menu_bar
  }


  def create_menu2(menu_bar: MenuBar): MenuBar = {
    var menu: MenuBar#MenuItem = menu_bar.addItem("Personen", null, null)

    menu.addItem("anzeigen", new Menu_Command('show))
    menu.addItem("leeren", new Menu_Command('clear))
    menu.addItem("test_fill", new Menu_Command('testfill))

    menu_bar
  }


  override def on_show = {
  }


  override def on_hide = {
  }

}
