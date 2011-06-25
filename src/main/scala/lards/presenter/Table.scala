package lards.presenter

import lards.view.Table
import lards.view.event.Edit
import lards.view.event.Main
import lards.view.Main
import lards.model.service.Service
import lards.model.dto.Person
import lards.global.Broadcaster


class Table(view: lards.view.Table, model_service: lards.model.service.Service) {
  
  private var selected_item_id: Long = -1

  view.init()
  Broadcaster.subscribe(this)



  def notify(event: Any) {
    println("table_presenter got event " + event)

    event match {

      case event: lards.view.event.Main => event.meaning match {
        case 'show => {
          view.set_data(model_service.get_persons()); 
          view.show 
        }
      }

      case event: lards.view.event.Table => event.meaning match {
        case 'delete => model_service.delete(selected_item_id)
        case 'edit => {
          println("edit")
          //if(!selected_item_id.isEmpty)
          Broadcaster.publish(new lards.presenter.event.Table(selected_item_id))
        }
        case 'new => {
          println("new")
          Broadcaster.publish(new lards.presenter.event.Table)
        }
        case 'select => {
          println("select=" + event.data)
          selected_item_id = event.data
        }
        case _ =>
      }

      case event: lards.model.event.Change => {
        selected_item_id = -1
        view.set_data(model_service.get_persons())
      }

      case _ =>

    }

  }


}
