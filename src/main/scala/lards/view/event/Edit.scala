package lards.view.event

import lards.global.Event
import lards.model.dto.Person


class Edit(override val meaning: Symbol, val data: Option[Person] = None) extends Event {
  def doSomething {
    println("bla")
  }
}
