/**
sent when a datasource changes
*/

package lards.model.event

import lards.global.Event

/**
meaning specifies the datasource
*/
class Change(override val meaning: Symbol) extends Event {
}
