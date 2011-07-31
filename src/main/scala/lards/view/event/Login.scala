/**
is sent when a user wants to login (e.g. presses login-button)
*/

package lards.view.event

import lards.global.Event


class Login(val username: Option[String], val password: Option[String]) extends Event {
  override val meaning = 'not_relevant
}
