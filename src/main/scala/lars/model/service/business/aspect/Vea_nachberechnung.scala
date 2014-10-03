/**
  aspects += create_aspect(2, "Versichertenanteil Nachberechnung", List('m1), List('m0))
*/

package lars.business_process.aspect

import scala.reflect.BeanProperty
import lars.model.dto.Tagable


object Vea_nachberechnung
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
