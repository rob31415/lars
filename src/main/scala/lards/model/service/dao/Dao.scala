package lards.model.service

import org.apache.ibatis.session.SqlSession
//import lards.model.dto.{Identifiable_long, Identifiable_string}


//@TODO: how can i say that A is subtype of something so that i can
//use the there-defined members and thus merge save-new and save-existing.
//get rid of save_new and save_existing and only have save.
trait Dao[A] {
  type Recordlist = Option[Seq[A]]


  def get_all: Recordlist = {
    val session = DbSessionProvider.factory.openSession()
    try{
      return _get_all(session)
    }finally {
      session.close
    }
    None
  }

  def _get_all(session: SqlSession): Recordlist

  def get(id: Long): Option[A] = {
    println("get(" + id + ")")
    //@TODO: this doesn't work due to type erasure
    //    if(id == -1) Some(new A()) else get_from_db(id)
    if(id == -1) None else get_from_db(id)
  }

  private def get_from_db(id: Long): Option[A] = {
    val session = DbSessionProvider.factory.openSession()
    try{
      _get(session, id)
    }finally {
      session.close
    }
    None
  }
  
  def _get(session: SqlSession, id: Long): Option[A]

  def save_new(record: A) {
    val session = DbSessionProvider.factory.openSession()

    try {
      println("saving new " + record)
      _save_new(session, record)
      session.commit
    } finally {
      session.close
    }
  }

  def _save_new(session: SqlSession, record: A)

  def save_existing(record: A) {
    val session = DbSessionProvider.factory.openSession()

    try {
      println("saving existing " + record)
      _save_existing(session, record)
      session.commit
    } finally {
      session.close
    }
  }

  def _save_existing(session: SqlSession, record: A)

  def delete(record: A) {
    val session = DbSessionProvider.factory.openSession()

    try {
      println("deleting record with id=" + record)
      _delete(session, record)
      session.commit
    } finally {
      session.close
    }
  }

  def _delete(session: SqlSession, record: A)

}
