/**
authenticates user
(that is check if username and password match any in the database)
and emitting Login-event if successful.

@TODO
*/

package lars.model

import lars.global.Applocal
import java.sql.Timestamp
import lars.global.Now
import lars.global.Logger


class Login extends Logger {

  def authenticate(username: String, password: String) {
    log_debug("authenticating. always positive. TODO.")
    //@TODO: get from database
    val user = new lars.model.dto.User(-1, Now.timestamp, username, username)
    Applocal.set_user(user)
    Applocal.broadcaster.publish(new lars.model.event.Login(user))
  }

}