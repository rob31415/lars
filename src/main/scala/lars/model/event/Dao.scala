/**
is sent when something in the database changes.
a event for a specific Dao is supposed to extend this.
*/

package lars.model.event

import lars.global.Event


class Dao(override val meaning: Symbol = 'undefined) extends Event {
}
