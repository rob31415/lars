/**
This thing can be used to broadcast events to every application
that is currently "running" (=for which a session exists).
*/
package lars.global

import lars.global.Publisher
import lars.global.Event


object Broadcaster extends Publisher[Event]