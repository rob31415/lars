package lards.model.service

import org.apache.ibatis.session.SqlSession
import collection.JavaConversions._
import java.util._
import lards.global.Applocal
import lards.model.dto.{User => Dto_user}
import lards.model.dto.Dto
import lards.model.dto.Dtos
import lards.model.event.{User => Event}
import java.sql.Timestamp
import lards.global.Now
import lards.model.dto.N_to_m_join



class User extends Dao {

  def _get_all(session: SqlSession, timestamp: Timestamp): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper.user.get_all", new java.sql.Timestamp(77,5,9,5,1,0,0)).asInstanceOf[java.util.ArrayList[Dto]]
    println("******************* USR" + data)
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get(session: SqlSession, id: Long, timestamp: Timestamp): Option[Dto_user] = {
    return Some( session.selectOne("lars.model.mybatis.mapper.user.get", new HashMap[Long, Timestamp](1).put(id, timestamp)).asInstanceOf[Dto_user] )
  }


  def _get_all_history(session: SqlSession, timestamp: Timestamp, filter_begin: Dto, filter_end: Dto): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper.user.get_all_history", timestamp).asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get_history(session: SqlSession, id: Long, timestamp: Timestamp): Option[Dto_user] = {
    return Some( session.selectOne("lars.model.mybatis.mapper.user.get_history", new HashMap[Long, Timestamp](1).put(id, timestamp)).asInstanceOf[Dto_user] )
  }


  def _save(session: SqlSession, record: Dto) {
    record.timestamp = Now.timestamp
    session.insert("lars.model.mybatis.mapper.user.insert", record)
    save_location(session, record.asInstanceOf[Dto_user])
  }


  def save_location(session: SqlSession, user: Dto_user) {
    //@TODO: if no locations insert "emtpy" user2location record
    user.location.foreach(
      (location) => {
        location.timestamp = user.timestamp
        session.insert("lars.model.mybatis.mapper.location.insert", location)
        session.insert("lars.model.mybatis.mapper.user2location.insert", new N_to_m_join(user, location))
      }
    )
  }


  def _overwrite(session: SqlSession, record: Dto) {
    session.update("lars.model.mybatis.mapper.user.update", record)
    session.update("lars.model.mybatis.mapper.user.location_update", record)
  }


  def _delete(session: SqlSession, record: Dtos) {
    session.delete("lars.model.mybatis.mapper.user.delete", record.asJava)
    session.delete("lars.model.mybatis.mapper.user.location_delete", record)
  }


  def on_success_delete() {
    Applocal.broadcaster.publish(new Event('deleted))
  }


  def on_success_save() {
    Applocal.broadcaster.publish(new Event('inserted))
  }


  def on_success_overwrite() {
    Applocal.broadcaster.publish(new Event('updated))
  }

}
