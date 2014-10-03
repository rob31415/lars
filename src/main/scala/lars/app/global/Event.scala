/**
an event that's sent around the whole application
*/

package lars.global

abstract class Event {
  def meaning: Symbol
}

