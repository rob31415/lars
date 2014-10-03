/**
standard-editwindow for Location-crud.
*/
package lars.view

import com.vaadin.ui._
import com.vaadin.data.util.BeanItemContainer
import collection.JavaConversions._
import lars.model.dto.{Location => Dto}
import lars.view.event.{Location => Event}
import lars.model.dto.Dtos


class Location(override val parent: Window)
  extends Editwindow(
  parent, 
  "Erfassungen > Orte",
  Map("description" -> "Bezeichnung"),
  List("description"),
  {() => new Dto()},
  {(meaning: Symbol, dtos: Dtos) => new Event(meaning, dtos)}
  ) {

  override def create_beanitem_container(data: Dtos): BeanItemContainer[_ <: lars.model.dto.Dto] = {
    new BeanItemContainer[Dto](classOf[Dto], data.get.get.asInstanceOf[scala.collection.Set[Dto]])
  }

  override def get_form_field_factory: Option[FormFieldFactory] = {
    None
  }

}
