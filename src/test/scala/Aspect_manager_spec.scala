import org.specs2.mutable._

import lars.model.dto.Transport
import lars.model.business_logic.Aspect_manager
import lars.model.service.Tag
import lars.model.service.Aspect


class Aspect_manager_spec extends Specification {


  "Transport" should {
    val model_tag = new Tag()
    val model_aspect = new Aspect(model_tag)

    val transport = new Transport(1)
    transport.tag.add(model_tag.get('x1).get)
    transport.tag.add(model_tag.get('m0).get)
    
    "2 Tags haben" in {
      transport.tag.size mustEqual 2
    }

    "Tag 'x1 haben" in {
      transport.tag.contains(model_tag.get('x1).get) mustEqual true
    }

    "Tag 'm0 haben" in {
      transport.tag.contains(model_tag.get('m0).get) mustEqual true
    }

    "keinen 'Versichertenanteil Nachberechnung' haben" in {
       Aspect_manager.is_true(
          model_aspect.get(2).get.asInstanceOf[lars.model.dto.Aspect], 
          transport) mustEqual Some(false)
    }

    "'Versichertenanteil Teilbezahlt' haben" in {
        Aspect_manager.is_true(
          model_aspect.get(1).get.asInstanceOf[lars.model.dto.Aspect], 
          transport) mustEqual Some(true)
    }

    "darf keine Aussage bzgl. Abrechenbarkeit machen" in {
        Aspect_manager.is_true(
          model_aspect.get(3).get.asInstanceOf[lars.model.dto.Aspect], 
          transport) mustEqual None
    }

  }


}
