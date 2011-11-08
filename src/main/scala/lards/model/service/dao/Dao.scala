/**
superclass for all data-access-objects.
unifies db-access and handles session-management.

all deriving classes have to override the 
methods of class Dao_overridable.

They are called from this class, respectively (hollywood principle).

@TODO: incorporate the modifying user!
*/

package lards.model.service

import org.apache.ibatis.session.SqlSession
import lards.model.dto.Dto
import lards.model.dto.Dtos
import java.sql.Timestamp
import lards.global.Now
import java.util._
import lards.global.Logger



trait Dao extends Dao_overrideable with Logger {


  /**
  returns for all ids the first record at/before the given timestamp
  */
  def get_all(timestamp: Timestamp = Now.timestamp): Dtos = {
    log_debug("get_all")
    val session = Database_session.factory.openSession()
    try{
      return _get_all(session, timestamp)
    }finally {
      session.close
    }
    new Dtos
  }


  /**
  returns for the given id the first record at/before the given timestamp
  */
  def get(id: Long, timestamp: Timestamp = Now.timestamp): Option[Dto] = {
    log_debug("get(" + id + ")")

    if(id == -1) {
      None
    } else {

      val session = Database_session.factory.openSession()
      try{
        return _get(session, id, timestamp)
      }finally {
        session.close
      }

      None
    }

  }
  
  
  /*
  returns for all ids all records at/before the given timestamp.
  @TODO: the result-set is reduced by given filters (id-range, time-range).
  */
  def get_all_history(timestamp: Timestamp = Now.timestamp, 
    filter_begin: Dto, filter_end: Dto): Dtos = {

    val session = Database_session.factory.openSession()
    try{
      return _get_all_history(session, timestamp, filter_begin, filter_end)
    }finally {
      session.close
    }
    new Dtos
  }


  /**
  returns for the given id all records at/before the given timestamp
  */
  def get_history(id: Long, timestamp: Timestamp = Now.timestamp): Option[Dtos] = {
    log_debug("Dao.get_history(" + id + ")")

    if(id == -1) {
      None
    } else {

      val session = Database_session.factory.openSession()
      try{
        return Some(_get_history(session, id, timestamp))
      }finally {
        session.close
      }

      None
    }

  }


  /**
  returns for the given id the timestamp of all modifications
  starting at the given time.
  */
  def get_modifications(id: Long, timestamp: Timestamp): Option[Dtos] = {
    //@TODO
    return None
  }


  /*
  saves record either under a new id (and current timestamp) or, 
  if id already exists, under same id and current timestamp.

  mybatis+postgresql note: if id is omited in sql-insert, postgresql serial generates new id.
  */
  def save(record: Dto) = {
    val session = Database_session.factory.openSession()

    // this is important, because it practically 
    // implements a new (modified) version of the same record.
    //record.timestamp = Now.timestamp

    try {
      log_debug("saving " + record)
      _save(session, record)
      session.commit
      on_success_save()
    } finally {
      session.close
    }
  }


  /*
  this updates a record (identified by id and timestamp).
  in n:n, the join-table is updated, too.
  this effectively modifies data-history.
  this does not generate modification-history.
  this is not inteded to be used by standard-users.
  */
  def overwrite(record: Dto) {
    val session = Database_session.factory.openSession()

    try {
      log_debug("overwriting " + record)
      _overwrite(session, record)
      session.commit
      on_success_overwrite()
    } finally {
      session.close
    }
  }


  /*
  this deletes one or more records.
  @TODO:
  if an id is given and no timestamp, all records with that id are deleted, effectively erasing their complete modification-history.
  if an id and a timestamp is given, only that one record is deleted, effectively modifying the modification-history.
  this does not generate modification-history.
  this is not inteded to be used by standard-users.
  */
  def delete(record: Dtos) {
    val session = Database_session.factory.openSession()

    try {
      log_debug("deleting records: " + record.get.get)
      _delete(session, record)
      session.commit
      on_success_delete()
    } finally {
      session.close
    }
  }
  
  
  def create_parameter_map(id: Long, timestamp: Timestamp) = {
    val param = new HashMap[String, String](2)
    param.put("id", id.toString)
    param.put("timestamp", timestamp.toString)
    param
  }

}
