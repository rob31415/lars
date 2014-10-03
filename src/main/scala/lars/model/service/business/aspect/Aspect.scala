/**
definition:
an aspect is a yes-or-no statement about a transport.
an aspect must be based solely on tags, not on data of the transport!

btw:
definition: a state of a transport is the sum of all aspects of that transport.
*/

package lars.business_process.aspect

import lars.model.dto.Tagable


abstract class Aspect {

  def is_true_for(transport: Tagable): Option[Boolean]

  // set aspect-specific tag/tags in a way that reflects that a certain aspect is fulfilled.
  def set_fulfilled_for(transport: Tagable)

  // set aspect-specific tag/tags in a way that reflects that a certain aspect is fulfilled.
  def set_unfulfilled_for(transport: Tagable)

  // remove al aspect-specific tag/tags completely.
  def remove_from(transport: Tagable)

}
