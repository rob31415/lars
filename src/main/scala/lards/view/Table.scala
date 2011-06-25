package lards.view

import lards.model.dto.Person
import lards.view.event._
import com.vaadin.ui._
import com.vaadin.data.Property
import java.util.Date 
import lards.global._
import collection.JavaConversions._
import com.vaadin.data.util.BeanItemContainer


class Table(parent: Window) 
  extends Publisher[lards.view.event.Table]
  with View {

  val window = new Window("Personenliste")

  private var button1 = new Button("New", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          Broadcaster.publish(new lards.view.event.Table('new))
        }
      }
    )

  private var button2 = new Button("Edit", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          Broadcaster.publish(new lards.view.event.Table('edit))
        }
      }
    )

  private var button3 = new Button("Delete", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          // @TODO: confirmdialog
          Broadcaster.publish(new lards.view.event.Table('delete))
        }
      }
    )

  private val table = create_table()


  override def init() = {
    println("table_view created")
    
    window.setWidth(500)
    window.setPositionX(10)
    window.setPositionY(40)

    window.addComponent(table)

    val button_bar = new HorizontalLayout();
    button_bar.setHeight("25px");
    button_bar.addComponent(button1)
    button_bar.addComponent(button2)
    button_bar.addComponent(button3)

    window.addComponent(button_bar)
    
    window.addListener(new Window.CloseListener() {
      def windowClose(event: Window#CloseEvent) {
        hide
      }
    })
  }
  
  
  override def on_show = {
    parent.addWindow(window)
  }


  override def on_hide = {
    parent.removeWindow(window)
  }


  def create_table(): com.vaadin.ui.Table = {
    val table = new com.vaadin.ui.Table("Persons");

    table.setSelectable(true);
    table.setNullSelectionAllowed(false)
    table.setImmediate(true);
    table.setHeight(100);
    //table.setMultiSelectMode(AbstractSelect.MultiSelectMode.DEFAULT)
    //MultiSelectMode.SIMPLE

    table.addContainerProperty("Name", classOf[String], null);
    table.addContainerProperty("Vorname", classOf[String], null);
    table.addContainerProperty("Telnr", classOf[String], null);

    table.addListener(new Property.ValueChangeListener() {
        def valueChange(event: Property.ValueChangeEvent) {
          println("table selection changed")
          //@TODO: obviously get rid of casting crap. but how?
          val value_from_java_object: java.lang.Long = event.getProperty.getValue.asInstanceOf[Long]
          Broadcaster.publish(new lards.view.event.Table('select, value_from_java_object))
        }
    })

    table
  }


  def set_data(persons: Seq[Person]) = {
    refill(persons)
  }

  
  def refill(persons: Seq[Person])
  {
    if(persons != null)
    {
      println("refill len=" + persons.length)
      table.removeAllItems()
      val container = new BeanItemContainer(classOf[Person], persons)
      table.setContainerDataSource(container)
/*      for (person <- persons) {
        //println("person: " + person.name + "," + person.surname + "," + person.phonenr)
        //table.addItem(person.toArray(), person.id)
        container.addItem(person)
      }
*/
    }
  }
  

}
