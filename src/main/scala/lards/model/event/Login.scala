package lards.model.event

import lards.global.Event
import lards.model.dto.User

class Login(val user: User, override val meaning: Symbol = 'undefined) extends Event /*extends Model*/ {
}
