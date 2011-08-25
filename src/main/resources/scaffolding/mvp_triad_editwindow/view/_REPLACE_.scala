/**
standard-editwindow for _REPLACE_-crud.
*/
package lards.view

import com.vaadin.ui._
import com.vaadin.data.util.BeanItemContainer
import collection.JavaConversions._
import lards.model.dto.{_REPLACE_ => Dto}
import lards.view.event.{_REPLACE_ => Event}
import lards.model.dto.Dtos


class _REPLACE_(override val parent: Window)
  extends Editwindow(
  parent, 
  "_REPLACE_",
  List("_REPLACE_"),
  {() => new Dto()},
  {(meaning: Symbol, dtos: Dtos) => new Event(meaning, dtos)}
  ) {

  override def create_beanitem_container(data: Dtos): BeanItemContainer[_ <: lards.model.dto.Dto] = {
    new BeanItemContainer[Dto](classOf[Dto], data.get.get.asInstanceOf[scala.collection.Set[Dto]])
  }

}
