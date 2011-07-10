package lards.presenter

import com.vaadin.Application 
import com.vaadin.ui._
import java.util.Date 
import java.lang.reflect._

import com.vaadin.terminal.gwt.server._
import javax.servlet.http._

import lards.view._
import lards.model._
import lards.presenter._
//import lards.global.Broadcaster
import lards.global.Applocal
import lards.view.event.Main


object Main {
  val threadLocal = new ThreadLocal[Main]()
}


class Main extends Application with HttpServletRequestListener { 

  private var window: Window = _
  private var view = new lards.view.Main()


  override def init {
    println("main init")

    Applocal.init(this)
    lards.presenter.Main.threadLocal.set(this)
    Applocal.broadcaster.subscribe(this)
    create_layout
    wire_up

    println("main init ok")
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


  def create_layout {
    window = new Window("LeistungsAbrechnungssystem RDS")
    window.getContent().setSizeFull()
    setMainWindow(window)
//    window.setContent(new lards.view.Login())
  }


  def wire_up {
    new lards.presenter.Login(new lards.view.Login(window), new lards.model.Login())
/*      new lards.presenter.Main(new view.Main(main_window))
    new lards.presenter.Table(new view.Table(main_window), model_service)
    new lards.presenter.Edit(new view.Edit(main_window), model_service)
*/
  }


  def notify(event: Any) {
    println("main-presenter got event " + event)

    event match {
      case event: lards.view.event.Main => {
        event.meaning match {
          case 'logout => {
            println("logging out user")
            //@TODO: yes/no dialog
            close()
          }
      
          case _ =>
        }
      }

      case event: lards.model.event.Login => {
        view.user_info.setValue("user: " + event.user.firstname)
        window.setContent(view)
      }
      
      case _ =>
    }
    
  }

}


