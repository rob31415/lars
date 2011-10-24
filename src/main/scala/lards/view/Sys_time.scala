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




class Sys_time(val parent: Window) extends View {

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
    println("view.Sys_time creating")
    window.setWidth(400)
    window.setHeight(250)
    window.setPositionX(10)
    window.setPositionY(40)


    checkbox.setImmediate(true)
    checkbox.addListener(new Property.ValueChangeListener() {
      def valueChange(event: Property.ValueChangeEvent) {
        if(checkbox.getValue().asInstanceOf[Boolean]) {
          datefield.setEnabled(false)
          button.setEnabled(false)
          Applocal.broadcaster.publish(new lards.view.event.Sys_time('use_os_time))
        } else {
          datefield.setValue(new java.util.Date())
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


    window.addComponent(checkbox)    
    window.addComponent(datefield)
    window.addComponent(button)


    window.getContent.setSizeFull

    window.addListener(new Window.CloseListener() {
      def windowClose(event: Window#CloseEvent) {
        Applocal.broadcaster.publish(new Event('close))
      }
    })

    println("view.Sys_time creating finished")
  }

}
