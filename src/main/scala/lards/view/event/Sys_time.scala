/**
*/

package lards.view.event

import lards.global.Event
import java.sql.Timestamp


class Sys_time(override val meaning: Symbol, val timestamp: Timestamp = null)
  extends lards.global.Event
