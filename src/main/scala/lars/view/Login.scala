/**
user can enter name and password and login to the system
*/

package lars.view

import event._
import event.Main
import com.vaadin.ui._
import lars.global.Applocal
import lars.global.Logger


/**
@emits lars.view.event.Login
*/
class Login(parent: Window) extends Panel with View with Logger {

  private var username: TextField = null
  private var password: TextField = null


  setSizeFull
  create_elements
  show


  override def on_show {
    log_debug("on_show() GEO=" + parent.getWidth() + ", " + parent.getHeight())
    parent.setContent(this)
  }


  override def on_hide {
    parent.setContent(null)
  }


  override def create_elements {
/*
    val layout = new GridLayout(3, 3)
    layout.setWidth("100%")
    layout.setHeight("100%")
    layout.addComponent(new Label("00"), 0, 0)
    layout.addComponent(new Label("01"), 0, 1)
    layout.addComponent(new Label("02"), 0, 2)

    layout.addComponent(new Label("10"), 1, 0)
    layout.addComponent(create_input_panel, 1, 1)
//    layout.setComponentAlignment(layout.getComponent(1, 1), Alignment.MIDDLE_CENTER)
    layout.addComponent(new Label("12"), 1, 2)

    layout.addComponent(new Label("20"), 2, 0)
    layout.addComponent(new Label("21"), 2, 1)
    layout.addComponent(new Label("22"), 2, 2)

//    layout.setComponentAlignment(layout.getComponent(1, 2), Alignment.BOTTOM_RIGHT)
//    layout.setComponentAlignment(layout.getComponent(0, 0), Alignment.BOTTOM_RIGHT)


    layout.setComponentAlignment(layout.getComponent(1, 2), Alignment.BOTTOM_RIGHT)
    layout.setComponentAlignment(layout.getComponent(0, 0), Alignment.BOTTOM_RIGHT)
    layout.getComponent(1, 2).setWidth("100%")
*/

    val layout = new AbsoluteLayout
    log_debug("create_elements(): GEO=" + parent.getWidth() + ", " + parent.getHeight())
    layout.setSizeFull  //doesn't work  @TODO: fix size while in application.init problem
    layout.setWidth("1000px")
    layout.setHeight("800px")
    layout.addComponent(create_input_panel, "left: 100px; top: 100px; " /*top: 50%; bottom: 50%;" */)

    addComponent(layout)    

    log_debug("create_elements() finished")
  }


  def create_input_panel(): Panel = {
    val panel = new Panel("LARS proof of concept: Transport-Abrechnungssystem")
    val layout = new HorizontalLayout
    layout.addComponent(create_username_input)
    layout.addComponent(create_password_input)
//    panel.addComponent(new Label("Bitte einloggen"))
    panel.addComponent(layout)
//    panel.addComponent(create_password_input)
    panel.addComponent(create_login_button)
    panel.setWidth("600px")
    panel.setHeight("150px")
    //panel.setSizeFull
    panel
  }


  private def create_username_input = {
    this.username = new TextField("Benutzername")
    this.username
  }


  private def create_password_input = {
    this.password = new TextField("Passwort")
    this.password.setSecret(true)
    this.password
  }


  private def create_login_button = {
    val button = new Button("Login", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          Applocal.broadcaster.publish(new lars.view.event.Login(Some(username.getValue.toString), Some(password.getValue.toString) ))
        }
      }
    )
    button
  }

}
