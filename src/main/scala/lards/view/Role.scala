/**
standard-editwindow for role-crud.
*/
package lards.view

import com.vaadin.ui._
import com.vaadin.data.util.BeanItemContainer
import collection.JavaConversions._
import lards.model.dto.{Role => Dto}
import lards.view.event.{Role => Event}
import lards.model.dto.Dtos


class Role(override val parent: Window)
  extends Editwindow(
  parent, 
  "Systemeinstellungen > Rollen",
  Map("description" -> "Bezeichnung"),
  List("description"),
  {() => new Dto()},
  {(meaning: Symbol, dtos: Dtos) => new Event(meaning, dtos)}
  ) {

  override def create_beanitem_container(data: Dtos): BeanItemContainer[_ <: lards.model.dto.Dto] = {
    new BeanItemContainer[Dto](classOf[Dto], data.get.get.asInstanceOf[scala.collection.Set[Dto]])
  }

  override def get_form_field_factory: Option[FormFieldFactory] = {
    None
  }

}
