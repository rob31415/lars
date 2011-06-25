package lards.presenter.event

import lards.global.Event

class Table(val data: Long = -1) extends Event {
  override val meaning = 'undefined
}
