/**
*/

package lars.view.event

import lars.global.Event
import java.sql.Timestamp


class Sys_time(override val meaning: Symbol, val timestamp: Timestamp = null)
  extends lars.global.Event
