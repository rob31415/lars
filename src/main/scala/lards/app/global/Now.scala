/*
gives you a timestamp of the current time
*/

package lards.global

import java.sql.Timestamp
import java.util.Calendar


object Now {
  def timestamp: Timestamp = new Timestamp( Calendar.getInstance().getTime().getTime() )
}
