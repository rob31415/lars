/**
implements the abstract base of an editwindow which is 
used throughout the application.
*/

package lards.view

import lards.view.event._
import com.vaadin.ui._
import lards.global.Applocal
import com.vaadin.data.Property
import com.vaadin.data.util.BeanItemContainer
import collection.JavaConversions._
import java.util.Collections
import com.vaadin.data.util.BeanItem

import lards.view.event.{Editwindow => Event}
import lards.model.dto.Dto
import lards.model.dto.Dtos



/**
@emits subtype of lards.view.event.Editwindow

type-specifications and arguments parametrize the window (e.g. to work with different dtos).
abstract methods are the means to specialise behaviour.

the unelegant, tedious and clumsy factory-workaround is used 
due to vexatious generics' type-erasure.
*/
abstract class Editwindow
  (val parent: Window,
  val title: String,
  val visible_item_props: List[String],
  val dto_factory: () => Dto,
  val event_factory: (Symbol, Dtos) => Event
  )
  extends Panel with View {

  /*
  every var that's declared here is intentionally put into
  "class-instance-scope" because it's being accessed
  all over the place, e.g. from inside ui-element-callbacks.
  (vaadin is a classical imperative gui toolkit.)
  */

  private var window: Window = null

  private var accordion: Accordion = null

  private var table: Table = null

  private var form_edit: Form = null  //@TODO: just 1 form for both, edit and new? no, i don't see a benefit (aug/11).
  private var panel_edit: Panel = null

  private var form_new: Form = null

  private var delete_button: Button = null
  private val number_of_selected = new Label()
  private val number_of_all = new Label()


  create_elements
  println("Editwindow-view created")


  override def on_show = {
    println("Editwindow on_show" + parent + "," + window)
    parent.addWindow(window)
  }


  override def on_hide = {
    println("Editwindow on_hide" + parent + "," + window)
    parent.removeWindow(window)
  }


  /*
  this happens at creation-time.

  instantiates all elements at the time when they are needed.

  this is done quasi on-demand because correct order and nesting is crucial.

  it's the create-function's responsibility to update the null-initialised
  instance-variables and thus putting the newly created element 
  in class-instance-scope.
  */
  override def create_elements {
    this.window = new Window(title)
    this.window.setWidth(500)
    this.window.setHeight(250)
    this.window.setPositionX(10)
    this.window.setPositionY(40)
    this.window.getContent.setSizeFull
    this.window.addComponent(create_accordion)
    this.window.addListener(new Window.CloseListener() {
      def windowClose(event: Window#CloseEvent) {
        //@TODO: provokes bug
        Applocal.broadcaster.publish(event_factory('close, new Dtos))
      }
    })
  }


  private def create_accordion(): Accordion = {
    val accordion = new Accordion()
      
    accordion.addTab(create_panel_table, "Übersicht", null)
    accordion.addTab(create_panel_edit, "Bearbeiten", null)
    accordion.addTab(create_panel_new, "Neu anlegen", null)
    accordion.addTab(create_panel_delete, "Löschen", null)
    accordion.addTab(new Label("TODO"), "Zeilenfilter einstellen", null)
    accordion.addTab(new Label("TODO"), "Spaltenaufbau einstellen", null)
    accordion.addTab(new Label("TODO"), "Sortierung einstellen", null)

    accordion.addListener(new TabSheet.SelectedTabChangeListener() {
      def selectedTabChange(event: TabSheet#SelectedTabChangeEvent) {

        if(accordion.getSelectedTab == panel_edit) {
          form_new.setItemDataSource(new BeanItem[Dto](dto_factory()))
          Applocal.broadcaster.publish(event_factory('start_modify, new Dtos))
        } else {
          Applocal.broadcaster.publish(event_factory('cancel_modify, new Dtos))
        }
      }
    })

    accordion.setSizeFull

    this.accordion = accordion
    accordion
  }


  private def create_panel_table = {
    val layout = new HorizontalLayout()
    layout.addComponent(create_row_filter_select())
    layout.addComponent(create_col_setup_select())
    layout.addComponent(create_sorting_select())

    val splitpanel = new VerticalSplitPanel()
    splitpanel.setFirstComponent(layout)
    splitpanel.setSecondComponent(create_table)
    //@TODO: get rid of magik-nr. should be height of layout
    splitpanel.setSplitPosition(50, com.vaadin.terminal.Sizeable.UNITS_PIXELS)
    splitpanel.setSizeFull

    splitpanel
  }


  private def create_row_filter_select(): ComboBox = {
    val box = new ComboBox("Zeilenfilter auswählen")
    box.addItem("Alles")
    box.addItem("Minimal")
    box
  }


  private def create_col_setup_select(): ComboBox = {
    val box = new ComboBox("Spaltenaufbau auswählen")
    box.addItem("Alles")
    box.addItem("Minimal")
    box
  }


  private def create_sorting_select(): ComboBox = {
    val box = new ComboBox("Sortierung auswählen")
    box.addItem("Sortierung A")
    box.addItem("Sortierung B")
    box
  }


  private def create_table(): com.vaadin.ui.Table = {
    val table = new com.vaadin.ui.Table("Datensätze")

    table.setSizeFull
    table.setSelectable(true)
    table.setNullSelectionAllowed(false)
    table.setImmediate(true)
    table.setMultiSelectMode(AbstractSelect.MultiSelectMode.DEFAULT)  //SIMPLE
    table.setMultiSelect(true)

    table.addListener(new Property.ValueChangeListener() {
      def valueChange(event: Property.ValueChangeEvent) {
        println("table selection changed " + table.getValue().getClass())
        val selected = event.getProperty.getValue.asInstanceOf[java.util.List[Dto]]
        accordion.getTab(1).setEnabled(selected.size == 1)
        Applocal.broadcaster.publish(event_factory('select, new Dtos(Some(selected))) )
      }
    })

    this.table = table
    table
  }


  private def create_panel_edit() = {
    val panel = new Panel()

    var button = new Button("Speichern", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          parent.showNotification("Datensatz wird gespeichert...")
          broadcast_save(form_edit)
        }
      }
    )
  
    panel.addComponent(create_form_edit)
    panel.addComponent(button)

    this.panel_edit = panel
    panel
  }


  private def create_form_edit = {
    this.form_edit = new Form()
    this.form_edit.setWriteThrough(true)
    this.form_edit.setReadThrough(true)
    this.form_edit.setImmediate(true)
    this.form_edit.setVisibleItemProperties(visible_item_props)
    this.form_edit
  }


  private def create_panel_new = {
    val panel = new Panel()

    var button = new Button("Neu Anlegen", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          parent.showNotification("Neuer Datensatz wird angelegt...")
          broadcast_save(form_new)
        }
      }
    )

    panel.addComponent(create_form_new)
    panel.addComponent(button)

    panel
  }


  private def create_form_new = {
    this.form_new = new Form()
    this.form_new.setVisibleItemProperties(visible_item_props)
    this.form_new
  }


  private def create_panel_delete(): Panel = {
    val panel = new Panel()

    this.delete_button = new Button("Ausgewählte Löschen",
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          parent.showNotification("Ausgewählte Löschen...")
          val selected = table.getValue.asInstanceOf[java.util.List[Dto]]
          Applocal.broadcaster.publish(event_factory('delete, new Dtos(Some(selected))) )
        }
      }
    )

    panel.addComponent(number_of_all)
    panel.addComponent(number_of_selected)
    panel.addComponent(this.delete_button)

    panel
  }


  //returns true if something was broadcasted
  private def broadcast_save(form: Form): Boolean = {
    val obj = get_bean_from_form(form)
    if(None != obj) {
      Applocal.broadcaster.publish(event_factory('save, wrap(obj.get)))
      return true
    } else {
      return false
    }
  }


  private def get_bean_from_form(form: Form): Option[Dto] = {
    val ds = form.getItemDataSource.asInstanceOf[BeanItem[Dto]]
    if(ds == null) None else Some(ds.getBean)
  }


  private def wrap(obj: Dto): Dtos = {
    val dtos = new java.util.ArrayList[Dto]()
    dtos.add(obj)
    new Dtos( Some(dtos) )
  }


  // for the table
  def set_data(data: Dtos) = {
    println("data=" + data)
    if(data != null) {
      table.removeAllItems()
      fill_table(data, table)
      table.setValue(table.firstItemId()) //autoselect first
    }
  }


  // BeanItemContainer needs a java.lang.Class parameter.
  // using "classOf[Dto]" as value for constructor-parameter 
  // "val class_of_dto: java.lang.Class[_ <: Dto]"
  // unfortunately is leading to a mysterious scala errormessage.
  // that's why the creation of BeanItemConatiner is done in
  // the deriving class
  def fill_table(dtos: Dtos, table: Table)


  // for editing
  def set_data(data: Dto) = {
    val bean_item = new BeanItem[Dto](data)
    form_edit.setItemDataSource(bean_item)
  }


  def set_number_of_selected_display(all: Integer, selected: Integer) = {
    number_of_all.setValue("Anzahl Datensätze insgesamt:" + all)
    number_of_selected.setValue("Anzahl ausgewählter Datensätze:" + selected)
    this.delete_button.setEnabled(selected != 0)
  }


  def get_current_edit_data(): Option[Dto] = {
    get_bean_from_form(form_edit)
  }


  def lock_edit {
    parent.showNotification("Datensatz wird gerade von einem anderen Benutzer editiert")
    //@TODO
  }


  def unlock_edit {
    //@TODO
  }

}
