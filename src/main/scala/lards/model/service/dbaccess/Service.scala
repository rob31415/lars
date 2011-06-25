package lards.model.service

import lards.model.dto._
import lards.global.Broadcaster
import lards.model.event._
import collection.JavaConversions._
import lards.model.dto.Person
import java.util.logging.Logger
import org.apache.ibatis.session._ //{SqlSessionFactoryBuilder,SqlSession}
import org.apache.ibatis.io._ //{SqlSessionFactoryBuilder,SqlSession}



class Service {

  var persons: Seq[Person] = null
  var factory: SqlSessionFactory = _



  def init {
    println("model init")
    
    open_connection    
    Broadcaster.publish(new lards.model.event.Init)
  }
  
  
  def open_connection {
    val resource: String = "mybatis/config.xml"
    val reader = Resources.getResourceAsReader(resource)
    val builder: SqlSessionFactoryBuilder = new SqlSessionFactoryBuilder()
    factory = builder.build(reader)
  }


  def populate_list() = {
    println("populate_list")

    val session = factory.openSession()

    try{
      persons = session.selectList("lards.model.dto.PersonMapper.getPersons").asInstanceOf[java.util.List[Person]]
      (0 to 100).toList.foreach(i => persons = persons :+ new Person(100+i, "name"+i.toString, "sur"+i.toString, "66"+i.toString) )
    }finally {
      session.close
    }
  }


  def get_persons(): Seq[Person] = {
    populate_list()
    persons
  }

  
  def get_person(id: Long): Person = {
    println("get_person(" + id + ")")

    if(id == -1) new Person else get_person_from_db(id)
  }


  //@TODO: look up book "clean code" regarding try-catch
  def get_person_from_db(id: Long): Person = {
    val session = factory.openSession()
    var person: Person = null

    try{
      person = session.selectOne("lards.model.dto.PersonMapper.getPerson", id).asInstanceOf[Person]
    }finally {
      session.close
    }
    person  
  }


  def save(person: Person) {
    val session = factory.openSession()

    try {
      if(person.id == -1) {
        println("saving new " + person)
        session.insert("lards.model.dto.PersonMapper.insertPerson", person)
      } else {
        println("saving existing " + person)
        session.update("lards.model.dto.PersonMapper.updatePerson", person)
      }
      session.commit
      Broadcaster.publish(new lards.model.event.Change)
    } finally {
      session.close
    }
  }


  def delete(id: Long) {
    if(id == -1) {
      println("can't delete non-existing person")
    } else {
      val session = factory.openSession()

      try {
        println("deleting person with id=" + id)
        session.delete("lards.model.dto.PersonMapper.deletePerson", id)
        session.commit
        Broadcaster.publish(new lards.model.event.Change)
      } finally {
        session.close
      }
    }
  }


}
