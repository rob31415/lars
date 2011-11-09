/**
  aspects += create_aspect(1, "Versichertenanteil Teilbezahlt", List('x1), List('x0))
*/

package lards.business_process.aspect

import lards.model.dto.Tagable


object Vea_teilbezahlt
  extends Aspect {


  override def is_true_for(transport: Tagable): Option[Boolean] = {
    return None
  }
  
  def set_fulfilled_for(transport: Tagable) {
    //@TODO
  }

  def set_unfulfilled_for(transport: Tagable) {
    //@TODO
  }

  def remove_from(transport: Tagable) {
    //@TODO
  }

}
