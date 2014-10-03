/**
Simple Login Dialog
*/

package lars.presenter

import lars.global.Applocal
import lars.view.Login
import lars.model.Login
import lars.global.Logger


class Login(view: lars.view.Login, model: lars.model.Login)
  extends Logger {

  Applocal.broadcaster.subscribe(this)

  def notify(event: Any) {
    log_debug("login-presenter got event " + event)

    event match {
      case event: lars.view.event.Login => {
        log_debug("username=" + event.username + ", password=" + event.password)        
        view.hide
        model.authenticate(event.username.get, event.password.get)
      }
      
      case _ =>
    }
    
  }

}