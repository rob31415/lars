package lards.model

import lards.global.Applocal
import java.sql.Timestamp
import lards.global.Now


class Login {

  def authenticate(username: String, password: String) {
    println("authenticating. always positive. TODO.")
    //@TODO: get from database
    val user = new lards.model.dto.User(-1, Now.timestamp, username, username)
    Applocal.set_user(user)
    Applocal.broadcaster.publish(new lards.model.event.Login(user))
  }

}