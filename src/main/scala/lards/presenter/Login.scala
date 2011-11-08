/**
Simple Login Dialog
*/

package lards.presenter

import lards.global.Applocal
import lards.view.Login
import lards.model.Login
import lards.global.Logger


class Login(view: lards.view.Login, model: lards.model.Login)
  extends Logger {

  Applocal.broadcaster.subscribe(this)

  def notify(event: Any) {
    log_debug("login-presenter got event " + event)

    event match {
      case event: lards.view.event.Login => {
        log_debug("username=" + event.username + ", password=" + event.password)        
        view.hide
        model.authenticate(event.username.get, event.password.get)
      }
      
      case _ =>
    }
    
  }

}