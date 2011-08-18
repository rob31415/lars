package lards.model

import lards.global.Applocal


class Login {

  def authenticate(username: String, password: String) {
    println("authenticating")
    //@TODO: get from database
    val user = new lards.model.dto.User(-1, username, username)
    Applocal.set_user(user)
    Applocal.broadcaster.publish(new lards.model.event.Login(user))
    println("authentication ok")
  }

}