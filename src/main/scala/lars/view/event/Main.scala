/**
is being sent when a user selects a menu-item.
item is identified by the "meaning" argument.
*/

package lars.view.event

import lars.global.Event


class Main(override val meaning: Symbol) extends Event {
}
