package lards.model.service

import org.apache.ibatis.session.SqlSession
import lards.model.dto.Dto
import lards.model.dto.Dtos


//@TODO: get rid of save_new and save_existing and only have save.
trait Dao {

  def get_all: Dtos = {
    val session = DbSessionProvider.factory.openSession()
    try{
      return _get_all(session)
    }finally {
      session.close
    }
    new Dtos
  }

  def _get_all(session: SqlSession): Dtos

  def get(id: Long): Option[Dto] = {
    println("get(" + id + ")")
    //@TODO: introduce a factory for this to let derived class create it's type-instance
    //if(id == -1) Some(new derived_dto()) else get_from_db(id)
    if(id == -1) None else get_from_db(id)
  }

  private def get_from_db(id: Long): Option[Dto] = {
    val session = DbSessionProvider.factory.openSession()
    try{
      _get(session, id)
    }finally {
      session.close
    }
    None
  }
  
  def _get(session: SqlSession, id: Long): Option[Dto]

  def save(record: Dto) = {
    if(record.id == -1)
      save_new(record)
    else
      save_existing(record)
  }

  def save_new(record: Dto) {
    val session = DbSessionProvider.factory.openSession()

    try {
      println("saving new " + record)
      _save_new(session, record)
      session.commit
      on_success_insert()
    } finally {
      session.close
    }
  }

  def _save_new(session: SqlSession, record: Dto)

  def save_existing(record: Dto) {
    val session = DbSessionProvider.factory.openSession()

    try {
      println("saving existing " + record)
      _save_existing(session, record)
      session.commit
      on_success_update()
    } finally {
      session.close
    }
  }

  def _save_existing(session: SqlSession, record: Dto)

  def delete(record: Dtos) {
    val session = DbSessionProvider.factory.openSession()

    try {
      println("deleting record with id=" + record.get.get)
      _delete(session, record)
      session.commit
      on_success_delete()
    } finally {
      session.close
    }
  }

  def _delete(session: SqlSession, record: Dtos)

  def on_success_delete()

  def on_success_update()
 
  def on_success_insert()

}
