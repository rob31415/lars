package lards.view

import event._
import event.Main
import com.vaadin.ui._
import lards.global.Applocal


class Login(window: Window) extends Panel with View {

  private val username = new TextField ("Username");
  private val password = new TextField ("Password");
  private val button = new Button("Login", 
    new Button.ClickListener() { 
      def buttonClick(event: Button#ClickEvent) {
        Applocal.broadcaster.publish(new lards.view.event.Login(Some(username.getValue.toString), Some(password.getValue.toString) ))
      }
    }
  )


  init
  window.setContent(this)
  
  
  override def init {
    password.setSecret(true)

    addComponent(new Label("Bitte einloggen"))
    addComponent(username)
    addComponent(password)
    addComponent(button)
    
    setSizeFull()

    println("login created")
  }


  override def on_show = {}


  override def on_hide = {}

}
