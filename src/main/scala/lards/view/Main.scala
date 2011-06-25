package lards.view

import event._
import com.vaadin.ui._
import lards.global.Broadcaster
import event.Main


class Main(parent: Window) extends View {

    class Menu_Command(val meaning: Symbol) extends MenuBar.Command {
      def menuSelected(selectedItem: MenuBar#MenuItem) {
        //label_info.setValue("Menu selection=" + selectedItem.getText())
        Broadcaster.publish(new lards.view.event.Main(meaning))
      }
    }


    override def init() = {
      println("main_view created")

      parent.addComponent(create_menu())
    }


    def create_menu(): MenuBar = {

      var default_command = new MenuBar.Command() {
        def menuSelected(selectedItem: MenuBar#MenuItem) {
          Broadcaster.publish(new lards.view.event.Main('todo))
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
