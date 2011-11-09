/**
Center of the Application.
Instanciates everything and wires everything up.
gives different users disjunct applications (memory).
*/

package lards.presenter

import com.vaadin.Application 
import com.vaadin.ui._

import com.vaadin.terminal.gwt.server._
import javax.servlet.http._

import lards.view._
import lards.model._
import lards.global.Applocal
import lards.global.Logger


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
  private var view: lards.view.Main = _


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
    lards.presenter.Main.threadLocal.set(this)

    Applocal.broadcaster.subscribe(this)

    //setTheme("bree")
    setTheme("runo")
    window = new Window("LARS - Das LeistungsAbrechnungssystem der RDS Saar")
    log_debug("window=" + window)

    setMainWindow(window)
    window.getContent.setSizeFull
    view = new lards.view.Main(window)

    wire_up

    log_debug("GEO=" + window.getWidth() + ", " + window.getHeight())

    log_debug("init finished")
  }


  //@TODO: do we need DI yet?
  def wire_up {

    //@TODO: probably put in global scope as static?
    val model_tag = new lards.model.service.Tag

    val model_role = new lards.model.service.Role
    val model_location = new lards.model.service.Location
    new lards.presenter.Login(new lards.view.Login(window), new lards.model.Login())
    new lards.presenter.Role( new lards.view.Role(window), model_role )
    new lards.presenter.Location( new lards.view.Location(window), model_location )
    new lards.presenter.User( new lards.view.User(window), new lards.model.service.User(), model_role, model_location )
    new lards.presenter.Sys_time( new lards.view.Sys_time(window) )
  }


  override def onRequestStart(request: HttpServletRequest, response: HttpServletResponse ) {
    if(lards.presenter.Main.threadLocal.get == null)
      lards.presenter.Main.threadLocal.set(this)
    log_debug("OnRequestStart(" + request.getQueryString() + ", " + request.getPathInfo() + ")")
    //setTheme("bree")
  }


  override def onRequestEnd(request: HttpServletRequest, response: HttpServletResponse ) {
    lards.presenter.Main.threadLocal.remove()
    log_debug("OnRequestEnd")
  }


  def notify(event: Any) {
    log_debug("notify(" + event + ")")

    event match {

      //something happend in the associated view (menu selection)
      case event: lards.view.event.Main => {    
          event.meaning match {
          case 'logout => {
            log_debug("logging out user")
            //@TODO: yes/no dialog
            Applocal.broadcaster.publish(new lards.presenter.event.Main('shutdown))
            Applocal.set_user(null)
            //@TODO: anything to clean-up in Applocal?
            close()
          }
          case 'about => {
            window.showNotification("Lars - das Leistungsabrechnungssystem der RDS-Saar !")
          }
      
          case _ =>
        }
      }

      // a user has succesfully logged-in
      case event: lards.model.event.Login => {
        view.user_info.setValue("user: " + event.user.firstname)
        view.show
      }
      
      case _ =>
    }
    
  }

}


