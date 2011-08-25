/**
is sent when something in the database changes.
*/

package lards.model.event

import lards.global.Event


class Dao(override val meaning: Symbol = 'undefined) extends Event {
}
