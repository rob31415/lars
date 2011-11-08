/**
let's user set the time the system thinks it's in.
*/

package lards.presenter

import lards.global.Applocal
import lards.view.event.{Editwindow => View_event}
import lards.global.Now
import lards.global.Logger



class Sys_time(view: lards.view.Sys_time)
  extends Logger {

  Applocal.broadcaster.subscribe(this)

  log_debug("constructor")



  def notify(event: Any) {
    log_debug("notify(" + event + ")")

    event match {

      // mainmenu-item selected
      
      case event: lards.view.event.Main => {
        
        if(event.meaning == 'sys_time) {
          if(view.is_shown) {
            view.hide
          } else {
            view.show
          }
        }
      }

      //something happend in the associated view

      case event: lards.view.event.Sys_time => {

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
      case event: lards.presenter.event.Main => {
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

