/**
hat häkchen "betriebssystem zeit", textfield & button.
wenn gesetzt: sys-zeitobjekt setzen, field&button disablen
wenn nicht gesetzt: f&b enablen
bei buttonklick: sys-zeitobjekt setzen
*/
package lards.view

import com.vaadin.ui._
import lards.view.event.{Sys_time => Event}
import com.vaadin.data.Property
import lards.global.Applocal
import java.sql.Timestamp
import java.util.Date
import lards.global.Now
import lards.global.Logger




class Sys_time(val parent: Window) extends View with Logger {

  val window = new Window("Systemeinstellungen / Applikationsweite Systemzeit")
  
  val checkbox = new CheckBox("Aktuelle Zeit des Betriebssystems benutzen")

  val datefield = new DateField("Systemzeit")

  val button = new Button("Zeit setzen") 

  create_elements


  override def on_show {
    parent.addWindow(window)
  }


  override def on_hide {
    parent.removeWindow(window)
  }


  override def create_elements {
    log_debug("create_elements()")
    window.setWidth(400)
    window.setHeight(250)
    window.setPositionX(10)
    window.setPositionY(40)


    checkbox.setValue(!Now.override_active)
    checkbox.setImmediate(true)
    checkbox.addListener(new Property.ValueChangeListener() {
      def valueChange(event: Property.ValueChangeEvent) {
        if(checkbox.getValue().asInstanceOf[Boolean]) {
          datefield.setEnabled(false)
          button.setEnabled(false)
          Applocal.broadcaster.publish(new lards.view.event.Sys_time('use_os_time))
        } else {
          //datefield.setValue(new java.util.Date())
          datefield.setEnabled(true)
          button.setEnabled(true)
        }
      }
    })

    button.addListener(new Button.ClickListener() { 
      def buttonClick(event: Button#ClickEvent) {
        parent.showNotification("Zeit wird gesetzt...")
        //make a timestamp out of a java-date
        val date = datefield.getValue.asInstanceOf[Date]
        val timestamp = new Timestamp(date.getTime)
        Applocal.broadcaster.publish(new lards.view.event.Sys_time('set_time, timestamp))
      }
    })
    button.setEnabled(Now.override_active)

    datefield.setEnabled(Now.override_active)
    datefield.setValue(new java.util.Date(Now.timestamp.getTime))

    window.addComponent(checkbox)    
    window.addComponent(datefield)
    window.addComponent(button)

    window.getContent.setSizeFull

    window.addListener(new Window.CloseListener() {
      def windowClose(event: Window#CloseEvent) {
        Applocal.broadcaster.publish(new Event('close))
      }
    })

    log_debug("create_elements() finished")
  }

}
