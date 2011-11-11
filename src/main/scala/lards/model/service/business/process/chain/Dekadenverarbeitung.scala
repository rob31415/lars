/**

*/

package lards.business_process.chain

import lards.global.Applocal
import lards.global.Logger



object Dekadenverarbeitung
  extends Logger {


  Applocal.broadcaster.subscribe(this)

  log_debug("creating, listening to menu_id 'dekadenverarbeitung")


  def notify(event: Any) {
    log_debug("got event " + event)

    event match {

      // mainmenu-item selected
      
      case event: lards.view.event.Main => {
        
        if(event.meaning == 'dekadenverarbeitung) {
          log_debug("TODO: implement Dekadenverarbeitung chain")
        }
      }


      // app' is going down
      case event: lards.presenter.event.Main => {
        event.meaning match {
          case 'shutdown => {
            log_warn("TODO: warn about cancelling if chain is running")
          }
    
          case _ =>
        }
      }

      case _ =>
    }

    log_debug("processing of event " + event + " finished")
  }


}

