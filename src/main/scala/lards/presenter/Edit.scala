package lards.presenter

import lards.view.Table
import lards.model.service.Service
import lards.model.dto.Person
import lards.global.Broadcaster


class Edit(
  private val view: lards.view.Edit,
  private val model_service: lards.model.service.Service) {

  private var test_person: Person = _

  view.init
  Broadcaster.subscribe(this)
  

  def notify(event: Any) {
    println("edit_presenter got event " + event)

    event match {
      case event: lards.presenter.event.Table => {
        view.set_data(model_service.get_person(event.data))
        view.show
      }
      
      case event: lards.model.event.Change => 
        if(view.is_shown) view.set_data(model_service.get_person(view.get_current_id))

      case event: lards.view.event.Edit => event.meaning match {
        case 'save => {                           // match on event's more precise message
          if(!event.data.isEmpty) {
            println("saving")
            model_service.save(event.data.get)
          } else {
            println("not saving")
          }
        }
        case 'clear => view.set_data(new Person)
        case _ =>
      }
      
      case _ =>
    }
    
  }

}
