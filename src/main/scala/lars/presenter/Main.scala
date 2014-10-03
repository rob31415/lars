/**
Center of the Application.
Instanciates everything and wires everything up.
gives different users disjunct applications (memory).
*/

package lars.presenter

import com.vaadin.Application 
import com.vaadin.ui._

import com.vaadin.terminal.gwt.server._
import javax.servlet.http._

import lars.view._
import lars.model._
import lars.global.Applocal
import lars.global.Logger


object Main {
  val threadLocal = new ThreadLocal[Main]()
}


class Main 
  extends Application 
  with HttpServletRequestListener 
  with Logger { 

  /**
    as an exception to the mvp rules, this presenter
    aggregates the gui-main-window.
    it's the container in which all mvp-views display themselves
  */
  private var window: Window = _
  private var view: lars.view.Main = _


  /**
    application entry point.
    this is being called from the servlet-container via
    vaadin-toolkit, the first time that a request from
    a certain client (browser) arrives
    note:
    after this, a repaintAll happens and it's only after that,
    the window has it's correct size
  */
  override def init {
    log_debug("init")

    Applocal.init(this)
    lars.presenter.Main.threadLocal.set(this)

    Applocal.broadcaster.subscribe(this)

    //setTheme("bree")
    setTheme("runo")
    window = new Window("LARS proof of concept: Transport-Abrechnungssystem")
    log_debug("window=" + window)

    setMainWindow(window)
    window.getContent.setSizeFull
    view = new lars.view.Main(window)

    wire_up

    log_debug("GEO=" + window.getWidth() + ", " + window.getHeight())

    log_debug("init finished")
  }


  //@TODO: do we need DI yet?
  def wire_up {

    //@TODO: probably put in global scope as static?
    val model_tag = new lars.model.service.Tag

    val model_role = new lars.model.service.Role
    val model_location = new lars.model.service.Location
    new lars.presenter.Login(new lars.view.Login(window), new lars.model.Login())
    new lars.presenter.Role( new lars.view.Role(window), model_role )
    new lars.presenter.Location( new lars.view.Location(window), model_location )
    new lars.presenter.User( new lars.view.User(window), new lars.model.service.User(), model_role, model_location )
    new lars.presenter.Sys_time( new lars.view.Sys_time(window) )
  }


  override def onRequestStart(request: HttpServletRequest, response: HttpServletResponse ) {
    if(lars.presenter.Main.threadLocal.get == null)
      lars.presenter.Main.threadLocal.set(this)
    log_debug("OnRequestStart(" + request.getQueryString() + ", " + request.getPathInfo() + ")")
    //setTheme("bree")
  }


  override def onRequestEnd(request: HttpServletRequest, response: HttpServletResponse ) {
    lars.presenter.Main.threadLocal.remove()
    log_debug("OnRequestEnd")
  }


  def notify(event: Any) {
    log_debug("notify(" + event + ")")

    event match {

      //something happend in the associated view (menu selection)
      case event: lars.view.event.Main => {    
          event.meaning match {
          case 'logout => {
            log_debug("logging out user")
            //@TODO: yes/no dialog
            Applocal.broadcaster.publish(new lars.presenter.event.Main('shutdown))
            Applocal.set_user(null)
            //@TODO: anything to clean-up in Applocal?
            close()
          }
          case 'about => {
            window.showNotification("LARS proof of concept: Transport-Abrechnungssystem")
          }
      
          case _ => window.showNotification("Funktion nicht implementiert")
        }
      }

      // a user has succesfully logged-in
      case event: lars.model.event.Login => {
        view.user_info.setValue("Hallo " + event.user.firstname + "! Willkommen zu LARS.")
        view.show
      }
      
      case _ =>
    }
    
  }

}


