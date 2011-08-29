/**
standard-editwindow for User-crud.
*/
package lards.view

import com.vaadin.ui._
import com.vaadin.data.util.BeanItemContainer
import com.vaadin.data.Item
import collection.JavaConversions._
import lards.model.dto.{User => Dto}
import lards.view.event.{User => Event}
import lards.model.dto.Dtos



class Factory extends FormFieldFactory {
  override def createField(item: Item, _property_id: Object, ui_context: Component): Field = {
    val property_id = _property_id.asInstanceOf[String]
    
    property_id match {
      case "firstname" => new TextField("Vorname")
      case "lastname" => new TextField("Nachname")
      case "role" => {
        val select = new Select("Rolle")
        select.addItem("rolle1")
        select.addItem("rolle2")
        select;
      }
      case _ => {
        println("NULL !!!!!!!!!!!!!!")
        null
      }
    }
  }
}


class User(override val parent: Window)
  extends Editwindow(
  parent, 
  "Bearbeitungsfenster: Einstellungen / Benutzer",
  List("firstname", "lastname"),
  {() => new Dto()},
  {(meaning: Symbol, dtos: Dtos) => new Event(meaning, dtos)}
  ) {


  override def create_beanitem_container(data: Dtos): BeanItemContainer[_ <: lards.model.dto.Dto] = {
    new BeanItemContainer[Dto](classOf[Dto], data.get.get.asInstanceOf[scala.collection.Set[Dto]])
  }

  
  override def create_form_field_factory: Option[FormFieldFactory] = {
    Some(new Factory)
  }


}
