/*
returns a timestamp of the time the system thinks it is in
this is in app-scope.
*/

package lards.global

import java.sql.Timestamp
import java.util.Calendar


object Now {

  var override_time: Timestamp = null //new Timestamp( Calendar.getInstance().getTime().getTime() )

  // new Timestamp(77,5,9,6,1,0,0)

  def timestamp: Timestamp = {
    if(override_time == null)
      new Timestamp( Calendar.getInstance().getTime().getTime() )
    else
      override_time
  }
 
  
  def set_time(timestamp: Timestamp) {
    override_time = timestamp
  }
 
  
  def use_os_time = override_time = null
  
  def override_active = override_time != null

}
