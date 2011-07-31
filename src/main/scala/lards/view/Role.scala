/**
*/

package lards.view

import event._
import com.vaadin.ui._
import lards.global.Applocal
import com.vaadin.data.Property
import com.vaadin.data.util.BeanItemContainer
import collection.JavaConversions._
import com.vaadin.data.util.BeanItem
//import java.util.Collections._
//import java.util._

/**
TODO: should we fill data in table only if view is shown?
would that be done by the view or rather the presenter?
*/
class Role(var parent: Window) extends Panel with View {

  val window = new Window("Einstellungen / Rollen")
  private val table = create_table()
  private val accordion = new Accordion()
  private val form_edit = new Form()
  private val form_new_creation = new Form()  //@TODO: only 1 form for edit and new

  init

  override def init {
    println("view.Role.init")
    
    window.setWidth(500)
    window.setHeight(250)
    window.setPositionX(10)
    window.setPositionY(40)
    
    accordion.setSizeFull
    
    accordion.addTab(create_panel_table(), "Rollen", null)
    accordion.addTab(create_panel_edit(), "Rolle bearbeiten", null)
    accordion.addTab(create_panel_new(), "Neue Rolle anlegen", null)
    accordion.addTab(new Label("TODO"), "Rollen löschen", null)
    accordion.addTab(new Label("TODO"), "Zeilenaufbau", null)
    accordion.addTab(new Label("TODO"), "Spaltenaufbau", null)

    window.addComponent(accordion)

    window.addListener(new Window.CloseListener() {
      def windowClose(event: Window#CloseEvent) {
        hide
      }
    })
    
    form_edit.setWriteThrough(true)
    form_edit.setReadThrough(true)
    form_edit.setImmediate(true)
  }


  def create_panel_new(): Panel = {
    val panel = new Panel()
    
    //@TODO: do this on accordion-selection-change
    form_new_creation.setItemDataSource(new BeanItem[lards.model.dto.Role](new lards.model.dto.Role()))
    form_new_creation.setVisibleItemProperties(List("description"))

    var button = new Button("Neu Anlegen", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          parent.showNotification("Neue Rolle wird angelegt...")
          val obj = form_new_creation.getItemDataSource.asInstanceOf[BeanItem[lards.model.dto.Role]].getBean
          Applocal.broadcaster.publish(new lards.view.event.Role('save, Some(obj)))
          //@TODO: do this on accordion-selection-change
          form_new_creation.setItemDataSource(new BeanItem[lards.model.dto.Role](new lards.model.dto.Role()))
        }
      }
    )
    
    panel.addComponent(form_new_creation)
    panel.addComponent(button)
    panel
  }
  

  def create_panel_edit(): Panel = {
    val panel = new Panel()

    var button = new Button("Speichern", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          parent.showNotification("Rolle wird gespeichert...")
          val obj = form_edit.getItemDataSource.asInstanceOf[BeanItem[lards.model.dto.Role]].getBean
          Applocal.broadcaster.publish(new lards.view.event.Role('save, Some(obj)))
        }
      }
    )
    
    panel.addComponent(form_edit)
    panel.addComponent(button)
    panel
  }


  def create_panel_table(): Panel = {
    val layout = new HorizontalLayout()

    layout.addComponent(create_row_filter_select())
    layout.addComponent(create_col_filter_select())
    
    val panel = new Panel()
    panel.addComponent(layout)
    panel.addComponent(table)
    
    panel
  }

  
  def create_row_filter_select(): ComboBox = {
    val box = new ComboBox("Zeilenfilter auswählen")
    box.addItem("Alles")
    box.addItem("Minimal")
    box
  }


  def create_col_filter_select(): ComboBox = {
    val box = new ComboBox("Spaltenaufbau auswählen")
    box.addItem("Alles")
    box.addItem("Minimal")
    box
  }
  
  
  override def on_show = {
    parent.addWindow(window)
  }


  override def on_hide = {
    parent.removeWindow(window)
  }


  def create_table(): com.vaadin.ui.Table = {
    val table = new com.vaadin.ui.Table("Rollen")

    table.setHeight(100)
//    table.setSizeFull
    table.setSelectable(true)
    table.setNullSelectionAllowed(false)
    table.setImmediate(true)
    table.setMultiSelectMode(AbstractSelect.MultiSelectMode.DEFAULT)
    table.setMultiSelect(true)
    //MultiSelectMode.SIMPLE

    table.addContainerProperty("Rollen Beschreibung", classOf[String], null)

    table.addListener(new Property.ValueChangeListener() {
        def valueChange(event: Property.ValueChangeEvent) {
          println("table selection changed " + table.getValue().getClass())
          
          val selected = event.getProperty.getValue.asInstanceOf[java.util.Set[lards.model.dto.Role]]

          if(selected.isEmpty) {
            accordion.getTab(1).setEnabled(false)
          } else {

            if(selected.size == 1) {
              accordion.getTab(1).setEnabled(true)
              val value = selected.iterator().next()
              Applocal.broadcaster.publish(new lards.view.event.Role('select, Some(value)))
            } else {
              accordion.getTab(1).setEnabled(false)
              //val value_from_java_object = event.getProperty.getValue.asInstanceOf[lards.model.dto.Role]
              //Applocal.broadcaster.publish(new lards.view.event.Role('select, Some(value_from_java_object)))
            }

          }
        }
    })

    table
  }


  // for the table
  def set_data(data: Seq[lards.model.dto.Role]) = {
    println("data=" + data)
    if(data != null)
    {
      table.removeAllItems()
      val container = new BeanItemContainer(classOf[lards.model.dto.Role], data)
      table.setContainerDataSource(container)
    }
  }


  def set_data(data: lards.model.dto.Role) = {  
    form_edit.setItemDataSource(new BeanItem[lards.model.dto.Role](data))
    form_edit.setVisibleItemProperties(List("description"));
  }

}
