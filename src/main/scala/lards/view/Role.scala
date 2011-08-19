/**
*/

package lards.view

import event._
import com.vaadin.ui._
import lards.global.Applocal


/**

*/
class Role(override val parent: Window) 
  extends Editwindow[lards.model.dto.Role, lards.view.event.Role, Option[java.util.Set[lards.model.dto.Role]]](
  parent, 
  "Bearbeitungsfenster: Einstellungen / Rollen",
  List("description"),
  {() => new lards.model.dto.Role()},
  {(meaning: Symbol, dtos: Option[java.util.Set[lards.model.dto.Role]]) => new lards.view.event.Role(meaning, dtos)},
  classOf[lards.model.dto.Role]
  ) {

}
