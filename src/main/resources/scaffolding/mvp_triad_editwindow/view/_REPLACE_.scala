/**
standard-editwindow for _REPLACE_-crud.
*/
package lars.view

import com.vaadin.ui._
import com.vaadin.data.util.BeanItemContainer
import collection.JavaConversions._
import lars.model.dto.{_REPLACE_ => Dto}
import lars.view.event.{_REPLACE_ => Event}
import lars.model.dto.Dtos
//@TODO: remove line if no 1:n is wanted
import lars.model.dto.{_REPLACE_ => _REPLACE_}
//@TODO: remove line if no n:n is wanted
import lars.model.dto.{_REPLACE_ => _REPLACE_}
//@TODO: remove line if no 1:n and no n:n is wanted
import com.vaadin.data.Item




//@TODO: remove whole class if no 1:n with automatic gui-field-naming 
// and no n:n is wanted.
// or alter constructor-arguments accordingly otherwise.
class Factory(_REPLACE_: _REPLACE_, _REPLACE_: _REPLACE_) extends FormFieldFactory {

  var _REPLACE_: Dtos = new Dtos


  override def createField(item: Item, _property_id: Object, ui_context: Component): Field = {
    val property_id = _property_id.asInstanceOf[String]
    
    property_id match {
    
      case _REPLACE_ => _REPLACE_
      
    }
  }

}



class _REPLACE_(override val parent: Window)
  extends Editwindow(
  parent, 
  "_REPLACE_",
  Map(_REPLACE_ -> _REPLACE_),
  List("_REPLACE_"),
  {() => new Dto()},
  {(meaning: Symbol, dtos: Dtos) => new Event(meaning, dtos)}
  ) {

  //@TODO: remove line if no 1:n and no n:n is wanted
  //@TODO: can usage of "null" be avoided?
  var factory: Option[Factory] = null

  override def create_beanitem_container(data: Dtos): BeanItemContainer[_ <: lars.model.dto.Dto] = {
    new BeanItemContainer[Dto](classOf[Dto], data.get.get.asInstanceOf[scala.collection.Set[Dto]])
  }


  override def get_form_field_factory: Option[Factory] = {
    //@TODO: always return None if no 1:n with automatic gui-field-naming 
    // and no n:n is wanted.
    if(factory == null) return None else factory
  }


  //@TODO: remove whole method if no 1:n with automatic gui-field-naming 
  // and no n:n is wanted.
  def create_form_field_factory(roles: Dtos, locations: Dtos) {
    factory = Some(new Factory(roles, locations))
  }

}
