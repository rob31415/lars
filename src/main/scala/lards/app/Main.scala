package lards

import com.vaadin.Application 
import com.vaadin.ui._
import java.util.Date 
import java.lang.reflect._

import view._
import model._
import presenter._


class Main extends Application { 

    private val main_window = new Window("eleventech prototype application")
    private val model_service = new lards.model.service.Service



    override def init {
      println("main init")
      create_layout
      wire_up
      model_service.init
      println("main init ok")
    }


    def create_layout {
      var layout_v = new VerticalLayout()
      main_window.setContent(layout_v);
      setMainWindow(main_window)		
      main_window.getContent().setSizeFull();
    }


    def wire_up {
      new lards.presenter.Main(new view.Main(main_window))
      new lards.presenter.Table(new view.Table(main_window), model_service)
      new lards.presenter.Edit(new view.Edit(main_window), model_service)
    }

}
