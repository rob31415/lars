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
import lards.model.dto.{Role => Role_dto}
import lards.model.dto.{Location => Location_dto}




class Factory(roles: Dtos, locations: Dtos) extends FormFieldFactory {


//  def this() = this(new Dtos(None))

  var user_locations: Dtos = new Dtos


  def set_user(dao: Dto) {
    //@TODO user_locations = dao.location
  }


  override def createField(item: Item, _property_id: Object, ui_context: Component): Field = {
    val property_id = _property_id.asInstanceOf[String]
    
    property_id match {
    
      case "firstname" => new TextField("Vorname")
      
      case "lastname" => new TextField("Nachname")
      
      case "role" => {
        val select = new Select("Rolle")
        select.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY)
        select.setItemCaptionPropertyId("description")
        select.setNullSelectionAllowed(false)
        select.setMultiSelect(false)
        select.setWriteThrough(true)
        select.setReadThrough(false)

        if(roles.get.isDefined && roles.get.get.size > 0) {
          select.setContainerDataSource(
            new BeanItemContainer[Role_dto](classOf[Role_dto], roles.get.get.asInstanceOf[scala.collection.Set[Role_dto]]))
          select.setEnabled(true)
        } else {
          select.setEnabled(false)
        }
  
        select
      }

      case "location" => {

//        val select = new TwinColSelectEx(classOf[Location], "Locations")
        val select = new TwinColSelect("Locations")
        select.setLeftColumnCaption("verfügbare")
        select.setRightColumnCaption("zugewiesen")
        select.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY)
        select.setItemCaptionPropertyId("description")
        //select.setNullSelectionAllowed(false)
        select.setMultiSelect(true)
        select.setWriteThrough(true)
        select.setReadThrough(true)
        select.setImmediate(true)

        if(locations.get.isDefined && locations.get.get.size > 0) {
          select.setContainerDataSource(
            new BeanItemContainer[Location_dto](classOf[Location_dto], locations.get.get.asInstanceOf[scala.collection.Set[Location_dto]]))
          //println("BBBBBBBBBBBBBBBBB" + select.removeContainerProperty("locations"))
          select.setEnabled(true)
        } else {
          select.setEnabled(false)
        }
  
        select
      }

    }
  }

}


class User(override val parent: Window)
  extends Editwindow(
  parent, 
  "Bearbeitungsfenster: Einstellungen / Benutzer",
  List("firstname", "lastname", "role"),
  {() => new Dto()},
  {(meaning: Symbol, dtos: Dtos) => new Event(meaning, dtos)}
  ) {
  
  // @TODO: can usage of "null" be avoided?
  var factory: Option[Factory] = null


  override def create_beanitem_container(data: Dtos): BeanItemContainer[_ <: lards.model.dto.Dto] = {
    new BeanItemContainer[Dto](classOf[Dto], data.get.get.asInstanceOf[scala.collection.Set[Dto]])
  }

  
  override def get_form_field_factory: Option[Factory] = {
    if(factory == null) return None else factory
  }


  def create_form_field_factory(roles: Dtos, locations: Dtos) {
    factory = Some(new Factory(roles, locations))
  }

}
