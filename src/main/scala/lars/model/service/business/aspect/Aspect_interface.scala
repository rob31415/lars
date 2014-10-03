/**
gives a transport the abilty to handle aspect-functionality.

supports the (imho more natural) way of thinking
"transport.set_fulfilled(aspect)"
rather than "aspect.set_fulfilled_for(transport)".

both accomplish the same thing.
*/

package lars.business_process.aspect

import lars.model.dto.Transport


trait Aspect_interface[A <: Transport] {

  def fulfills(aspect: Aspect): Option[Boolean] = aspect.is_true_for(this.asInstanceOf[A])

  def set_fulfilled(aspect: Aspect) = aspect.set_fulfilled_for(this.asInstanceOf[A])

  def set_unfulfilled(aspect: Aspect) = aspect.set_unfulfilled_for(this.asInstanceOf[A])

  def remove(aspect: Aspect) = aspect.remove_from(this.asInstanceOf[A])
}
