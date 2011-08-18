/**
user can enter name and password and login to the system
*/

package lards.view

import event._
import event.Main
import com.vaadin.ui._
import lards.global.Applocal


/**
@emits lards.view.event.Login
*/
class Login(window: Window) extends Panel with View {

  private var username: TextField = null
  private var password: TextField = null


  create_elements
  show


  override def on_show = {
    window.setContent(this)
  }


  override def on_hide = {
    window.setContent(null)
  }


  override def create_elements {

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

    addComponent(layout)    
    setSizeFull()

    layout.setComponentAlignment(layout.getComponent(1, 2), Alignment.BOTTOM_RIGHT)
    layout.setComponentAlignment(layout.getComponent(0, 0), Alignment.BOTTOM_RIGHT)
    layout.getComponent(1, 2).setWidth("100%")

    println("login created")
  }


  def create_input_panel(): Panel = {
    val panel = new Panel
    panel.addComponent(new Label("Bitte einloggen"))
    panel.addComponent(create_username_input)
    panel.addComponent(create_password_input)
    panel.addComponent(create_login_button)
//    panel.setWidth("100px")
    panel
  }


  private def create_username_input = {
    this.username = new TextField("Username")
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
          Applocal.broadcaster.publish(new lards.view.event.Login(Some(username.getValue.toString), Some(password.getValue.toString) ))
        }
      }
    )
    button
  }

}
