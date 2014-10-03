/**
let's user set the time the system thinks it's in.
*/

package lars.presenter

import lars.global.Applocal
import lars.view.event.{Editwindow => View_event}
import lars.global.Now
import lars.global.Logger



class Sys_time(view: lars.view.Sys_time)
  extends Logger {

  Applocal.broadcaster.subscribe(this)

  log_debug("constructor")



  def notify(event: Any) {
    log_debug("notify(" + event + ")")

    event match {

      // mainmenu-item selected
      
      case event: lars.view.event.Main => {
        
        if(event.meaning == 'sys_time) {
          if(view.is_shown) {
            view.hide
          } else {
            view.show
          }
        }
      }

      //something happend in the associated view

      case event: lars.view.event.Sys_time => {

        event.meaning match {
          case 'set_time => {
            // 6/9/77 05:00:00.000 AM
            Now.set_time(event.timestamp)
          }
          case 'use_os_time => {
            Now.use_os_time
          }

          case _ =>
        }
          
      }


      // app' is going down
      case event: lars.presenter.event.Main => {
        event.meaning match {
          case 'shutdown => {
            view.hide
          }
    
          case _ =>
        }
      }

      case _ =>
    }

    log_debug("notify() finished")
  }
  

}

