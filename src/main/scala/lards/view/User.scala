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
import lards.model.dto.{Role => Foreign_dto}




class Factory(roles: Dtos) extends FormFieldFactory {


  def this() = this(new Dtos(None))


  override def createField(item: Item, _property_id: Object, ui_context: Component): Field = {
    val property_id = _property_id.asInstanceOf[String]

    
    property_id match {
      case "firstname" => new TextField("Vorname")
      case "lastname" => new TextField("Nachname")
      case "role" => {
        val select = new NativeSelect("Rolle")
        select.setItemCaptionMode(AbstractSelect.ITEM_CAPTION_MODE_PROPERTY)
        select.setItemCaptionPropertyId("description")
        select.setNullSelectionAllowed(false)
        select.setMultiSelect(false)
        select.setWriteThrough(true)
        select.setReadThrough(true)

        if(roles.get.isDefined && roles.get.get.size > 0) {
          select.setContainerDataSource(
            new BeanItemContainer[Foreign_dto](classOf[Foreign_dto], roles.get.get.asInstanceOf[scala.collection.Set[Foreign_dto]])
          )
          select.setEnabled(true)

          if(ui_context.isInstanceOf[lards.view.Form_index]) {
            ui_context.asInstanceOf[lards.view.Form_index].get_selected
            //@TODO
          }

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
  
  var factory: Option[FormFieldFactory] = None


  override def create_beanitem_container(data: Dtos): BeanItemContainer[_ <: lards.model.dto.Dto] = {
    new BeanItemContainer[Dto](classOf[Dto], data.get.get.asInstanceOf[scala.collection.Set[Dto]])
  }

  
  override def create_form_field_factory: Option[FormFieldFactory] = {
    // constructor of superclass is executed before this constructor.
    // in the meantime, factory is null and also, this is called.
    // @TODO: can usage of "null" be avoided?
    if(factory == null) factory = Some(new Factory)    
    factory
  }


  def set_role_data(roles: Dtos) {
    factory = Some(new Factory(roles))
  }


  def set_edit_form_role_selection(dto: Dto) {
    val field = form_edit.getField("role").asInstanceOf[NativeSelect]

    println("user.set_edit_form_role_selection before "+field.getValue)

    //@TODO what's wrong? why doesn't this work?
    //field.select(dto.role)

    //@TODO what's wrong? why doesn't this work either?

    println("user.set_edit_form_role_selection dto,role "+dto+", "+dto.role)
    val id = field.getItemIds().find({ e => e.asInstanceOf[Foreign_dto].equals(dto.role) })  //; println("e="+e)
    println("user.set_edit_form_role_selection id "+id.get)
    val item = field.getItem( id.get )
    println("user.set_edit_form_role_selection item "+item)
    field.setValue(item)


    println("user.set_edit_form_role_selection after "+field.getValue)
  }


}
