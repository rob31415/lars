/**
is sent when user successfully logs in
*/

package lars.model.event

import lars.global.Event

class Login(val user: lars.model.dto.User, override val meaning: Symbol = 'undefined) 
  extends Event