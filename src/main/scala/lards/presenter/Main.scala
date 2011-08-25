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


object Main {
  val threadLocal = new ThreadLocal[Main]()
}


class Main extends Application with HttpServletRequestListener { 

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
  */
  override def init {
    println("presenter.Main init")

    Applocal.init(this)
    lards.presenter.Main.threadLocal.set(this)

    Applocal.broadcaster.subscribe(this)

    window = new Window("LeistungsAbrechnungssystem RDS")
    println("presenter.Main init window=" + window)

    setMainWindow(window)
    view = new lards.view.Main(window)
    window.getContent.setSizeFull

    wire_up

    println("main init ok")
  }


  def wire_up {
    new lards.presenter.Login(new lards.view.Login(window), new lards.model.Login())
    new lards.presenter.Role( new lards.view.Role(window), new lards.model.service.Role() )
  }


  override def onRequestStart(request: HttpServletRequest, response: HttpServletResponse ) {
    if(lards.presenter.Main.threadLocal.get == null)
      lards.presenter.Main.threadLocal.set(this)
    println("OnRequestStart:" + request.getQueryString() + ", " + request.getPathInfo())
  }


  override def onRequestEnd(request: HttpServletRequest, response: HttpServletResponse ) {
    lards.presenter.Main.threadLocal.remove()
    println("OnRequestEnd")
  }


  def notify(event: Any) {
    println("main-presenter got event " + event)

    event match {

      //something happend in the associated view (menu selection)
      case event: lards.view.event.Main => {    
          event.meaning match {
          case 'logout => {
            println("logging out user")
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


