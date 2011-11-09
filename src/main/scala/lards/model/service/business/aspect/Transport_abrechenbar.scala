/**
  aspects += create_aspect(3, "Tagable berechenbar", List('rg), List('ru, 're))
*/

package lards.business_process.aspect

import scala.reflect.BeanProperty
import lards.model.dto.Tagable


object Vea_berechenbar
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
