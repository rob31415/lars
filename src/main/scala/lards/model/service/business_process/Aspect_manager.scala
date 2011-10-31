/**
The Aspect-manager works like an oracle that you can
ask "is aspect X true for this transport".
it will answer yes, no or "can't say" (because no appropriate process processed the transport).
*/

package lards.model.business_logic

//import lards.global.Applocal
import lards.model.dto.Transport
import lards.model.dto.Aspect


object Aspect_manager {

  def is_true(aspect: Aspect, subject: Transport): Option[Boolean] = {
    return None
  }

}
