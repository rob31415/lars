/**
This thing can be used to broadcast events to every application
that is currently "running" (=for which a session exists).
*/
package lards.global

import lards.global.Publisher
import lards.global.Event


object Broadcaster extends Publisher[Event]