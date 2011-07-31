/**
an event that's sent around the whole application
*/

package lards.global

abstract class Event {
  def meaning: Symbol
}

