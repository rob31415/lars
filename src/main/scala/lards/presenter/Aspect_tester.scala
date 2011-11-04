/**
*/

package lards.presenter

import lards.global.Applocal
import lards.model.dto.Transport
import lards.model.business_logic.Aspect_manager



class Aspect_tester(model_tag: lards.model.service.Tag,
  model_aspect: lards.model.service.Aspect) {

  Applocal.broadcaster.subscribe(this)

  def notify(event: Any) {

    event match {

      // mainmenu-item selected
    
      case event: lards.view.event.Main => {

        if(event.meaning == 'do_aspect_test) {

          val transport = new Transport(1)
          transport.tag.add(model_tag.get('m1).get)

          println("Aspect_tester: " + 
            Aspect_manager.is_true(
              model_aspect.get(1).get.asInstanceOf[lards.model.dto.Aspect], 
              transport))

        }
      }
      
      case _ =>
    }

  }

}

