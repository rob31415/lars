/**
is being sent when a user selects a menu-item.
item is identified by the "meaning" argument.
*/

package lards.view.event

import lards.global.Event


class Main(override val meaning: Symbol) extends Event {
}
