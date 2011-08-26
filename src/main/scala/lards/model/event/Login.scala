/**
is sent when user successfully logs in
*/

package lards.model.event

import lards.global.Event

class Login(val user: lards.model.dto.User, override val meaning: Symbol = 'undefined) extends Event {
}
