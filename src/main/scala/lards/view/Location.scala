/**
standard-editwindow for Location-crud.
*/
package lards.view

import com.vaadin.ui._
import com.vaadin.data.util.BeanItemContainer
import collection.JavaConversions._
import lards.model.dto.{Location => Dto}
import lards.view.event.{Location => Event}
import lards.model.dto.Dtos


class Location(override val parent: Window)
  extends Editwindow(
  parent, 
  "Bearbeitungsfenster: Einstellungen / Locations",
  List("description"),
  {() => new Dto()},
  {(meaning: Symbol, dtos: Dtos) => new Event(meaning, dtos)}
  ) {

  override def create_beanitem_container(data: Dtos): BeanItemContainer[_ <: lards.model.dto.Dto] = {
    new BeanItemContainer[Dto](classOf[Dto], data.get.get.asInstanceOf[scala.collection.Set[Dto]])
  }

}
