/**
The Aspect-manager works like an oracle that you can
ask "is aspect X true for this transport".
it will answer yes, no or "can't say" (because no appropriate process processed the transport).

@TODO: bridge main-instanciated triads and global/static services.
*/

package lards.model.business_logic

import lards.model.dto.Transport
import lards.model.dto.Aspect
import collection.JavaConversions._


object Aspect_manager {

  // see if tags of given aspect match a given transport as follows:
  // returns false if at least one of the aspect's prohibitive tags is attached to the transport
  // returns true if all of the aspect's imperative tags is attached to the transport
  // returns "None" if no prohibitive and not all imperative tags are attached.
  def is_true(aspect: Aspect, subject: Transport): Option[Boolean] = {
    if( (aspect.tag_prohibitive.intersect(subject.tag)).size > 0 ) return Option(false);
    if( (aspect.tag_imperative.intersect(subject.tag)).size == subject.tag.size ) return Option(true);
    return None
  }

}
