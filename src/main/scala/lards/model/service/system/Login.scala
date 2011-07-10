package lards.model

import lards.global.Applocal


class Login {

  def authenticate(username: String, password: String) {
    println("authenticating")
    Applocal.broadcaster.publish(new lards.model.event.Login(new lards.model.dto.User(-1, username, username)))
    println("authenticating ok")
  }

}