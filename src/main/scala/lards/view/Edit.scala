package lards.view

import lards.model.dto.Person
import event._
import com.vaadin.ui._
import com.vaadin.ui.Window._
import com.vaadin.data.util.BeanItem
import java.util.Date 
import lards.global._
import collection.JavaConversions._



class Edit(parent: Window) extends View {

  private val window = new Window("Person")
  private val form = new Form();


  override def init() = {
    println("table_edit created")

    window.setWidth(270)
    window.setPositionX(520)
    window.setPositionY(40)

    setup_form

    window.addListener(new Window.CloseListener() {
        def windowClose(event: Window#CloseEvent) {
          hide
        }
    })

    window.addComponent(form)
  }


  def setup_form : Form = {

    form.setWriteThrough(true);
    form.setReadThrough(true);

    form.setFooter(new VerticalLayout());
    form.getFooter().addComponent(new Label("Bitte wählen Sie wie es weitergehen soll"));

    val okbar = new HorizontalLayout();
    okbar.setHeight("25px");
    form.getFooter().addComponent(okbar);

    var button1 = new Button("Speichern", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          hide
          parent.showNotification("Speichern...")
          Broadcaster.publish(new lards.view.event.Edit('save, Some(get_bean_data())))
          reset
        }
      }
    )

/*
    var button2 = new Button("Leeren", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          //Broadcaster.publish(new lards.view.event.Edit('clear))
          parent.showNotification("Eingaben leeren...")
          get_bean_data.clear
        }
      }
    )
*/
  
    okbar.addComponent(button1);
//    okbar.addComponent(button2);
    okbar.setComponentAlignment(button1, Alignment.TOP_RIGHT);

    form
  }
    
  
  override def on_show = {
    parent.addWindow(window)
  }


  override def on_hide = {
    parent.removeWindow(window)
  }


  def set_data(person: Person) = {
    if(person.id == -1)
      form.setCaption("Person anlegen")
    else
      form.setCaption("Person bearbeiten")

    form.setDescription("Personendaten für Person mit id " + person.id);

    form.setItemDataSource(new BeanItem[Person](person))
    form.setVisibleItemProperties(List("mainname","surname","phonenr"));
  }
 

  def get_bean_data(): Person = {
    form.getItemDataSource.asInstanceOf[BeanItem[Person]].getBean
  }


  def get_current_id: Long = {
    get_bean_data().id
  }
  
  def reset {
    set_data(new Person)
  }

}
