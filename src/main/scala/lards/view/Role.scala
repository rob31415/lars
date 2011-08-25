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
  "Bearbeitungsfenster: Einstellungen / Rollen",
  List("description"),
  {() => new Dto()},
  {(meaning: Symbol, dtos: Dtos) => new Event(meaning, dtos)}
  ) {

  override def fill_table(data: Dtos, table: Table) {
      val container = new BeanItemContainer(classOf[lards.model.dto.Dto], data.get.get)
      table.setContainerDataSource(container)
  }

}
