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


/**
TODO: should we fill data in table only if view is shown?
would that be done by the view or rather the presenter?
@emits lards.view.event.Role
*/
class Role(var parent: Window) extends Panel with View {

  val window = new Window("Bearbeitungsfenster: Einstellungen / Rollen")
  private val number_of_selected = new Label()
  private val number_of_all = new Label()
  private var delete_button = new Button("Ausgewählte Löschen",
    new Button.ClickListener() { 
      def buttonClick(event: Button#ClickEvent) {
        parent.showNotification("Ausgewählte Löschen...")
        val selected = table.getValue.asInstanceOf[java.util.Set[lards.model.dto.Role]]
        Applocal.broadcaster.publish(new lards.view.event.Role('delete, Some(selected)))
      }
    }
  )
  private val form_edit = new Form()
  private val panel_edit = create_panel_edit()
  private val form_create = new Form()  //@TODO: only 1 form for edit and new
  private val table = create_table()
  private val accordion = create_accordion()
  private var i_have_aquired_lock = false //@TODO get rid of this


  init

  override def init {
    println("view.Role.init")
    
    window.setWidth(500)
    window.setHeight(250)
    window.setPositionX(10)
    window.setPositionY(40)
  
    window.addComponent(accordion)

    window.addListener(new Window.CloseListener() {
      def windowClose(event: Window#CloseEvent) {
        //hide
        //unlock_dto(get_bean_from_form(form_edit))
        Applocal.broadcaster.publish(new lards.view.event.Role('close))
      }
    })
    
    form_edit.setWriteThrough(true)
    form_edit.setReadThrough(true)
    form_edit.setImmediate(true)
  }
  
  
  private def create_accordion(): Accordion = {
    val accordion = new Accordion()
    
    accordion.setSizeFull
    
    accordion.addTab(create_panel_table(), "Rollen Übersicht", null)
    accordion.addTab(panel_edit, "Rolle bearbeiten", null)
    accordion.addTab(create_panel_new(), "Neue Rolle anlegen", null)
    accordion.addTab(create_panel_delete(), "Rollen löschen", null)
    accordion.addTab(new Label("TODO"), "Zeilenfilter einstellen", null)
    accordion.addTab(new Label("TODO"), "Spaltenaufbau einstellen", null)
    accordion.addTab(new Label("TODO"), "Sortierung einstellen", null)

    accordion.addListener(new TabSheet.SelectedTabChangeListener() {
      def selectedTabChange(event: TabSheet#SelectedTabChangeEvent) {

        if(accordion.getSelectedTab == panel_edit) {
          Applocal.broadcaster.publish(new lards.view.event.Role('start_modify))
        } else {
          Applocal.broadcaster.publish(new lards.view.event.Role('cancel_modify))
        }

/*
        val dto = get_bean_from_form(form_edit)
        if(accordion.getSelectedTab == panel_edit) {
          if(lards.global.Editlock.contains(dto)) {
            parent.showNotification("Rolle wird momentan editiert!")
            //@TODO: lock form input
          } else {
            lards.global.Editlock.add(dto)
            i_have_aquired_lock = true
          }
        } else {
          unlock_dto(dto)
        }
*/

      }
    })
    
    accordion
  }


  private def create_panel_new(): Panel = {
    val panel = new Panel()
    
    //@TODO: do this on accordion-selection-change
    form_create.setItemDataSource(new BeanItem[lards.model.dto.Role](new lards.model.dto.Role()))
    form_create.setVisibleItemProperties(List("description"))

    var button = new Button("Neu Anlegen", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          parent.showNotification("Neue Rolle wird angelegt...")
          broadcast_save(form_create)
          //Applocal.broadcaster.publish(new lards.view.event.Role('save, put_in_new_list(get_bean_from_form(form_create))))
          //@TODO: do this on accordion-selection-change
          form_create.setItemDataSource(new BeanItem[lards.model.dto.Role](new lards.model.dto.Role()))
        }
      }
    )
    
    panel.addComponent(form_create)
    panel.addComponent(button)
    panel
  }


  //returns true if something was broadcasted
  private def broadcast_save(form: Form): Boolean = {
    val obj = get_bean_from_form(form)
    if(None != obj) {
      Applocal.broadcaster.publish(new lards.view.event.Role('save, put_in_new_list(obj.get)))
      return true
    } else {
      return false
    }
  }


  private def get_bean_from_form(form: Form): Option[lards.model.dto.Role] = {
    val ds = form.getItemDataSource.asInstanceOf[BeanItem[lards.model.dto.Role]]
    if(ds == null) None else Some(ds.getBean)
  }
  
/*
  def pack_create_form_data() = {
    //val obj = form_create.getItemDataSource.asInstanceOf[BeanItem[lards.model.dto.Role]].getBean
    put_in_new_list(get_bean_from_form(form_create))
  }

  def pack_edit_form_data() = {
    val obj = form_edit.getItemDataSource.asInstanceOf[BeanItem[lards.model.dto.Role]].getBean
    put_in_new_list(obj)
  }
*/  

  private def put_in_new_list(obj: lards.model.dto.Role) = {
    val dto_list = new java.util.HashSet[lards.model.dto.Role]
    dto_list.add(obj)
    Some(dto_list)
  }


  private def create_panel_edit(): Panel = {
    val panel = new Panel()

    var button = new Button("Speichern", 
      new Button.ClickListener() { 
        def buttonClick(event: Button#ClickEvent) {
          parent.showNotification("Rolle wird gespeichert...")
          //val obj = form_edit.getItemDataSource.asInstanceOf[BeanItem[lards.model.dto.Role]].getBean
          //Applocal.broadcaster.publish(new lards.view.event.Role('save, put_in_new_list(get_bean_from_form(form_edit))))
          broadcast_save(form_edit)
        }
      }
    )
    
    panel.addComponent(form_edit)
    panel.addComponent(button)
        
    panel
  }


  private def create_panel_table() = {
    val panel = new Panel()
    val layout = new HorizontalLayout()

    layout.addComponent(create_row_filter_select())
    layout.addComponent(create_col_setup_select())
    layout.addComponent(create_sorting_select())

    panel.addComponent(layout)

    val splitpanel = new SplitPanel()
    splitpanel.setOrientation(SplitPanel.ORIENTATION_VERTICAL)
    splitpanel.setFirstComponent(panel)
    splitpanel.setSecondComponent(table)
    splitpanel.setSplitPosition(50)

    splitpanel
  }

  
  private def create_panel_delete(): Panel = {
    val panel = new Panel()

    panel.addComponent(number_of_all)
    panel.addComponent(number_of_selected)
    panel.addComponent(delete_button)

    panel
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
  
  
  override def on_show = {
    parent.addWindow(window)
  }


  override def on_hide = {
    parent.removeWindow(window)
  }


  private def create_table(): com.vaadin.ui.Table = {
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
        accordion.getTab(1).setEnabled(selected.size == 1)
        Applocal.broadcaster.publish(new lards.view.event.Role('select, Some(selected)))
      }
    })

    table
  }


  // for the table
  def set_data(data: Seq[lards.model.dto.Role]) = {
    println("data=" + data)
    if(data != null) {
      table.removeAllItems()
      val container = new BeanItemContainer(classOf[lards.model.dto.Role], data)
      table.setContainerDataSource(container)
      table.setValue(table.firstItemId()) //autoselect first
    }
  }


  // for editing
  def set_data(data: lards.model.dto.Role) = {
    val bean_item = new BeanItem[lards.model.dto.Role](data)
    form_edit.setItemDataSource(bean_item)
    form_edit.setVisibleItemProperties(List("description"));

/* TODO this isn't called when a textfield is modified. that's a pity.

    bean_item.addListener(new com.vaadin.data.Item.PropertySetChangeListener() {
      def itemPropertySetChange(event: com.vaadin.data.Item.PropertySetChangeEvent) {
        parent.showNotification("!!!")
        println("!!!!!!!!!!!!!!!!!")
      }
    })
*/
  }


  def set_number_of_selected_display(all: Integer, selected: Integer) = {
    number_of_all.setValue("Anzahl Datensätze insgesamt:" + all)
    number_of_selected.setValue("Anzahl ausgewählter Datensätze:" + selected)
    delete_button.setEnabled(selected != 0)
  }


  def get_current_edit_data(): Option[lards.model.dto.Role] = {
    get_bean_from_form(form_edit)
    //form_edit.setItemDataSource
  }

/*
  private def get_current_create_data(): Option[lards.model.dto.Role] = {
    //get_bean_from_form(form_create)
    //form_edit.setItemDataSource
  }
*/
}
