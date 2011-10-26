/**
implements the abstract base of an editwindow which is 
used throughout the application as a standard-view
for crud of any entity.
*/

package lards.view

import lards.view.event._
import com.vaadin.ui._
import lards.global.Applocal
import com.vaadin.data.Property
import com.vaadin.data.util.BeanItemContainer
import collection.JavaConversions._
import java.util._
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
  val visible_fields_table: Map[String, String],  //dto-field id, table-header string
  val visible_fields_edit: List[String],
  val dto_factory: () => Dto,
  val event_factory: (Symbol, Dtos) => Event
  )
  extends View {

  /*
  every var that's declared here is intentionally put into
  "class-instance-scope" because it's being accessed
  all over the place, e.g. from inside ui-element-callbacks.
  (vaadin is a classic imperative gui toolkit.)
  */

  private var window: Window = null

  private var select_primary: TabSheet = null
  private var select_secondary: Accordion = null

  private var table: Table = null
  private var table_history: Table = null

  //@TODO: just 1 form for both, edit and new? 
  //no, i don't see a benefit (rg/aug/11).
  var form_edit: Form = null
  private var panel_edit: Panel = null

  private var form_new: Form = null
  private var panel_new: Panel = null

  private var delete_button: Button = null
  private val number_of_selected = new Label()
  private val number_of_all = new Label()


  create_elements


  override def on_show {
    println("Editwindow on_show " + parent + ", " + window)
    parent.addWindow(window)
  }


  override def on_hide {
    println("Editwindow on_hide " + parent + ", " + window)
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
    println("view.Editwindow creating")
    window = new Window(title)
    println("view.Editwindow window=" + window)
    window.setWidth(400)
    window.setHeight(400)
    window.setPositionX(10)
    window.setPositionY(40)
    window.addComponent(create_select_primary)
    window.getContent.setSizeFull
    window.addListener(new Window.CloseListener() {
      def windowClose(event: Window#CloseEvent) {
        Applocal.broadcaster.publish(event_factory('close, new Dtos))
      }
    })
    println("view.Editwindow creating finished")
  }


  private def create_select_primary(): TabSheet = {
    val select_primary = new TabSheet()

    select_primary.addTab(create_select_secondary, "Übersicht", null)
    select_primary.addTab(create_panel_edit, "Bearbeiten", null)
    select_primary.addTab(create_panel_new, "Neu anlegen", null)
    create_panel_delete
    //@TODO select_primary.addTab(create_panel_delete, "Löschen", null)
    //select_primary.addTab(new Label("TODO"), "Zeilenfilter einstellen", null)
    //select_primary.addTab(new Label("TODO"), "Spaltenaufbau einstellen", null)
    //select_primary.addTab(new Label("TODO"), "Sortierung einstellen", null)


    select_primary.addListener(new TabSheet.SelectedTabChangeListener() {
      def selectedTabChange(event: TabSheet#SelectedTabChangeEvent) {
        
        //@TODO: how to get case to work here? this code is smelly!

        val edit_tab = event.getTabSheet.getTab(1).getComponent()
        val new_tab = event.getTabSheet.getTab(2).getComponent()

        if(event.getTabSheet.getSelectedTab == edit_tab) {
          // println says this is null. @TODO: learn why (what's different inside a closure/anonymous class than outside. is there a pitfall?)
          println("form_new inside selectedTabChange closure="+form_new)

          Applocal.broadcaster.publish(event_factory('start_modify, new Dtos))
        } else {
          Applocal.broadcaster.publish(event_factory('cancel_modify, new Dtos))
        }
        
        if(event.getTabSheet.getSelectedTab == new_tab) {
          form_new.setItemDataSource(new BeanItem[Dto](dto_factory()))
          form_new.setVisibleItemProperties(visible_fields_edit)
        } 
        
      }
    })


    select_primary.setSizeFull
    //initially: no edit because nothing is selected in the table
    select_primary.getTab(1).setEnabled(false)
    this.select_primary = select_primary
    select_primary
  }


  private def create_select_secondary = {
    val select_secondary = new Accordion()

    select_secondary.addTab(create_panel_table, "Übersicht", null)
    select_secondary.addTab(create_panel_table_history, "Historie", null)

    select_secondary.setSizeFull
    //initially: no history because nothing is selected in the table
    select_secondary.getTab(1).setEnabled(false)


    select_secondary.addListener(new TabSheet.SelectedTabChangeListener() {
      def selectedTabChange(event: TabSheet#SelectedTabChangeEvent) {
        
        val history_tab = event.getTabSheet.getTab(1).getComponent()

        if(event.getTabSheet.getSelectedTab == history_tab) {
          // no edit when in history tab
          select_primary.getTab(1).setEnabled(false)
        } else {
          // depends on selection in table
          select_primary.getTab(1).setEnabled(get_selected.get.get.size == 1)
        }

      }
    })    
    
    this.select_secondary = select_secondary
    select_secondary
  }


  private def create_panel_table = {
    val splitpanel = new VerticalSplitPanel()
    splitpanel.setFirstComponent(create_toolbar)
    splitpanel.setSecondComponent(create_table)
    splitpanel.setSplitPosition(0, com.vaadin.terminal.Sizeable.UNITS_PIXELS)
    splitpanel.setSizeFull

    splitpanel
  }

  
  private def create_panel_table_history = {
    val splitpanel = new VerticalSplitPanel()
    splitpanel.setFirstComponent(create_toolbar)
    splitpanel.setSecondComponent(create_table_history)
    splitpanel.setSplitPosition(0, com.vaadin.terminal.Sizeable.UNITS_PIXELS)
    splitpanel.setSizeFull

    splitpanel
  }


  private def create_toolbar = {
    val layout = new HorizontalLayout()
    layout.addComponent(create_row_filter_select())
    layout.addComponent(create_col_setup_select())
    layout.addComponent(create_sorting_select())
    layout.addComponent(new CheckBox("Nur markierte anzeigen"))
    layout.addComponent(new Button("Alle selektieren"))
    layout.addComponent(new Button("Alle deselektieren"))
    layout.addComponent(new Button("Einstellungen"))
    layout.setWidth("100%")
    layout
  }


  //@TODO
  private def create_row_filter_select(): ComboBox = {
    val box = new ComboBox("Zeilenfilter auswählen")
    box.addItem("Alles")
    box.addItem("Minimal")
    box
  }


  //@TODO
  private def create_col_setup_select(): ComboBox = {
    val box = new ComboBox("Spaltenaufbau auswählen")
    box.addItem("Alles")
    box.addItem("Minimal")
    box
  }


  //@TODO
  private def create_sorting_select(): ComboBox = {
    val box = new ComboBox("Sortierung auswählen")
    box.addItem("Sortierung A")
    box.addItem("Sortierung B")
    box
  }


  private def create_table(): com.vaadin.ui.Table = {
    val table = new com.vaadin.ui.Table("")

    table.setSizeFull
    table.setSelectable(true)
    table.setNullSelectionAllowed(false)
    table.setImmediate(true)
    table.setMultiSelectMode(AbstractSelect.MultiSelectMode.DEFAULT)  //SIMPLE
    table.setMultiSelect(true)
    table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_EXPLICIT)

    table.addListener(new Property.ValueChangeListener() {
      def valueChange(event: Property.ValueChangeEvent) {
        println("table selection changed " + table.getValue().getClass())
        val selected = get_selected
        select_primary.getTab(1).setEnabled(selected.get.get.size == 1)
        select_secondary.getTab(1).setEnabled(selected.get.get.size == 1)
        Applocal.broadcaster.publish(event_factory('select, selected))
      }
    })

    this.table = table
    table
  }


  private def get_selected: Dtos = {
    new Dtos(Some(table.getValue.asInstanceOf[java.util.Set[Dto]]))
  }


  private def create_table_history(): com.vaadin.ui.Table = {
    val table = new com.vaadin.ui.Table("")

    table.setSizeFull
    table.setSelectable(true)
    table.setImmediate(true)
    table.setMultiSelect(false)
    table.setColumnHeaderMode(Table.COLUMN_HEADER_MODE_EXPLICIT)

    table.addListener(new Property.ValueChangeListener() {
      def valueChange(event: Property.ValueChangeEvent) {
        println("table_history selection changed")
        //val selected = get_selected
        //Applocal.broadcaster.publish(event_factory('select, selected))
      }
    })

    this.table_history = table
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
  
    panel.setSizeFull
    panel.addComponent(create_form_edit)
    panel.addComponent(button)

    this.panel_edit = panel
    panel
  }


  private def create_form_edit = {
    this.form_edit = new Form()

    val factory = get_form_field_factory
    if(factory.isDefined) this.form_edit.setFormFieldFactory(factory.get)

    this.form_edit.setWriteThrough(true)
    this.form_edit.setReadThrough(true)
    this.form_edit.setImmediate(true)
    this.form_edit.setSizeFull

    this.form_edit
  }


  private def create_panel_new = {
    this.panel_new = new Panel()

    var button = new Button("Neu Anlegen", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          parent.showNotification("Neuer Datensatz wird angelegt...")
          broadcast_save(form_new)
        }
      }
    )

    this.panel_new.setSizeFull
    this.panel_new.addComponent(create_form_new)
    this.panel_new.addComponent(button)

    this.panel_new
  }


  private def create_form_new = {
    this.form_new = new Form()
    
    val factory = get_form_field_factory
    if(factory.isDefined) this.form_new.setFormFieldFactory(factory.get)

    this.form_new.setSizeFull

    this.form_new
  }


  def get_form_field_factory: Option[FormFieldFactory]


  private def create_panel_delete(): Panel = {
    val panel = new Panel()

    this.delete_button = new Button("Ausgewählte Löschen",
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          parent.showNotification("Ausgewählte Löschen...")
          Applocal.broadcaster.publish(event_factory('delete, get_selected))
        }
      }
    )

    panel.setSizeFull
    panel.addComponent(number_of_all)
    panel.addComponent(number_of_selected)
    panel.addComponent(this.delete_button)

    panel
  }


  //returns wether something was broadcasted
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
    val dtos = new java.util.HashSet[Dto]()
    dtos.add(obj)
    new Dtos(Some(dtos))
  }


  def rebuild {
    println("view.Editwindow.rebuild")

    panel_new.removeComponent(form_new)
    panel_new.addComponent(create_form_new)

    panel_edit.removeComponent(form_edit)
    panel_edit.addComponent(create_form_edit)
  }


  // for the table
  def set_data(data: Dtos) {
    println("view.Editwindow.set_data(" + data + ")")
    
    //@TODO: what's with this null ?
    if(data != null) {
      table.removeAllItems
      table.setContainerDataSource(create_beanitem_container(data))
      
      table.setVisibleColumns(visible_fields_table.keySet.toArray)
      visible_fields_table.foreach((el) => table.setColumnHeader(el._1, el._2))

      /* is ugly and doesn't fire valueChange event
      val set = new java.util.HashSet[Dto]
      set.add( table.firstItemId().asInstanceOf[Dto] )
      table.setValue(set) //autoselect first
      */

    }
  }


  def create_beanitem_container(dtos: Dtos): BeanItemContainer[_ <: Dto]


  // for editing and history
  def set_data(data_edit: Dto, data_history: Dtos) {
    val bean_item = new BeanItem[Dto](data_edit)
    form_edit.setItemDataSource(bean_item)
    form_edit.setVisibleItemProperties(visible_fields_edit)

    //@TODO: what's with this null ?
    if(data_history != null) {
      table_history.removeAllItems
      table_history.setContainerDataSource(create_beanitem_container(data_history))

      // add timestamp col
      var visible_col_set = visible_fields_table.keySet + "timestamp"
      table_history.setVisibleColumns(visible_col_set.toArray)
      visible_fields_table.foreach((el) => table_history.setColumnHeader(el._1, el._2))
      table_history.setColumnHeader("timestamp", "timestamp")
    }
  }


  def set_number_of_selected_display(all: Integer, selected: Integer) = {
    number_of_all.setValue("Anzahl Datensätze insgesamt:" + all)
    number_of_selected.setValue("Anzahl ausgewählter Datensätze:" + selected)
    this.delete_button.setEnabled(selected != 0)
  }


  def get_current_edit_data: Option[Dto] = {
    get_bean_from_form(form_edit)
  }


  // restores to default
  // @TODO: invent system to store app-state
  def restore_view_state {
    select_primary.setSelectedTab(select_primary.getTab(0).getComponent)
  }


  def lock_edit {
    //@TODO
    parent.showNotification("Datensatz wird gerade von einem anderen Benutzer editiert")
    form_edit.setEnabled(false)
  }


  def unlock_edit {
    //@TODO
    form_edit.setEnabled(true)
  }

}
