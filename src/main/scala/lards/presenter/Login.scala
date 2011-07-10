package lards.presenter

import lards.global.Applocal
import lards.view.Login
import lards.model.Login


class Login(view: lards.view.Login, model: lards.model.Login) {

  Applocal.broadcaster.subscribe(this)

  def notify(event: Any) {
    println("login-presenter got event " + event)

    event match {
      case event: lards.view.event.Login => {
        println("username=" + event.username + ", password=" + event.password)        
        model.authenticate(event.username.get, event.password.get)
      }
      
      case _ =>
    }
    
  }

}