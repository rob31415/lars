/**
implements the abstract base of an editwindow which is 
used throughout the application.
*/

package lards.view

import event._
import com.vaadin.ui._
import lards.global.Applocal
import com.vaadin.data.Property
import com.vaadin.data.util.BeanItemContainer
import collection.JavaConversions._
import com.vaadin.data.util.BeanItem


/**
@emits subtype of lards.view.event.Editwindow

type-specifications and arguments parametrize the window (e.g. to work with different dtos).
abstract methods are the means to specialise behaviour.

the unelegant, tedious and clumsy factory-workaround is used 
due to vexatious generics' type-erasure.
*/
class Editwindow
  [Dto, Notification_event <: lards.view.event.Editwindow[Dto], Dtos >: Option[java.util.Set[Dto]]]
  (val parent: Window,
  val title: String,
  val visible_item_props: List[String],
  val dto_type_factory: () => Dto,
  val event_type_factory: (Symbol, Dtos) => Notification_event,
  val class_of_dto: Class[Dto])
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


  override def on_show = {
    parent.addWindow(window)
  }


  override def on_hide = {
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
        Applocal.broadcaster.publish(event_type_factory('close, None))
      }
    })
  }


  private def create_accordion(): Accordion = {
    val accordion = new Accordion()
        
    accordion.addTab(create_panel_table, "Rollen Übersicht", null)
    accordion.addTab(create_panel_edit, "Rolle bearbeiten", null)
    accordion.addTab(create_panel_new, "Neue Rolle anlegen", null)
    accordion.addTab(create_panel_delete, "Rollen löschen", null)
    accordion.addTab(new Label("TODO"), "Zeilenfilter einstellen", null)
    accordion.addTab(new Label("TODO"), "Spaltenaufbau einstellen", null)
    accordion.addTab(new Label("TODO"), "Sortierung einstellen", null)

    accordion.addListener(new TabSheet.SelectedTabChangeListener() {
      def selectedTabChange(event: TabSheet#SelectedTabChangeEvent) {

        if(accordion.getSelectedTab == panel_edit) {
          form_new.setItemDataSource(new BeanItem[Dto](dto_type_factory()))
          Applocal.broadcaster.publish(event_type_factory('start_modify, None))
        } else {
          Applocal.broadcaster.publish(event_type_factory('cancel_modify, None))
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
    val table = new com.vaadin.ui.Table("Rollen")

    table.setSizeFull
    table.setSelectable(true)
    table.setNullSelectionAllowed(false)
    table.setImmediate(true)
    table.setMultiSelectMode(AbstractSelect.MultiSelectMode.DEFAULT)  //SIMPLE
    table.setMultiSelect(true)

    table.addContainerProperty("Rollen Beschreibung", classOf[String], null)

    table.addListener(new Property.ValueChangeListener() {
      def valueChange(event: Property.ValueChangeEvent) {
        println("table selection changed " + table.getValue().getClass())
        val selected = event.getProperty.getValue.asInstanceOf[java.util.Set[Dto]]
        accordion.getTab(1).setEnabled(selected.size == 1)
        Applocal.broadcaster.publish(event_type_factory('select, Some(selected)))
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
          parent.showNotification("Rolle wird gespeichert...")
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
          parent.showNotification("Neue Rolle wird angelegt...")
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
          val selected = table.getValue.asInstanceOf[java.util.Set[Dto]]
          Applocal.broadcaster.publish(event_type_factory('delete, Some(selected)))
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
      Applocal.broadcaster.publish(event_type_factory('save, put_in_new_list(obj.get)))
      return true
    } else {
      return false
    }
  }


  private def get_bean_from_form(form: Form): Option[Dto] = {
    val ds = form.getItemDataSource.asInstanceOf[BeanItem[Dto]]
    if(ds == null) None else Some(ds.getBean)
  }
  

  private def put_in_new_list(obj: Dto) = {
    val dto_list = new java.util.HashSet[Dto]
    dto_list.add(obj)
    Some(dto_list)
  }


  // for the table
  def set_data(data: Seq[Dto]) = {
    println("data=" + data)
    if(data != null) {
      table.removeAllItems()
      val container = new BeanItemContainer(class_of_dto, data)
      table.setContainerDataSource(container)
      table.setValue(table.firstItemId()) //autoselect first
    }
  }


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
