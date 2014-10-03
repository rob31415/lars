/**

*/

package lars.business_process.chain

import lars.global.Applocal
import lars.global.Logger



object Dekadenverarbeitung
  extends Logger {


  Applocal.broadcaster.subscribe(this)

  log_debug("creating, listening to menu_id 'dekadenverarbeitung")


  def notify(event: Any) {
    log_debug("got event " + event)

    event match {

      // mainmenu-item selected
      
      case event: lars.view.event.Main => {
        
        if(event.meaning == 'dekadenverarbeitung) {
          log_debug("TODO: implement Dekadenverarbeitung chain")
        }
      }


      // app' is going down
      case event: lars.presenter.event.Main => {
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

