package lards.model.service

import org.apache.ibatis.session.SqlSession
import collection.JavaConversions._
import java.util._
import lards.global.Applocal
import lards.model.dto.{Location => Dto_location}
import lards.model.dto.Dto
import lards.model.dto.Dtos
import lards.model.event.{Location => Event}
import java.sql.Timestamp



class Location extends Dao {

  def _get_all(session: SqlSession, timestamp: Timestamp): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper.location.get_all", timestamp).asInstanceOf[java.util.ArrayList[Dto]]
    println("******************* LOC " + data)
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get(session: SqlSession, id: Long, timestamp: Timestamp): Option[Dto_location] = {
    return Some( session.selectOne("lars.model.mybatis.mapper.location.get", new HashMap[Long, Timestamp](1).put(id, timestamp)).asInstanceOf[Dto_location] )
  }


  def _get_all_history(session: SqlSession, timestamp: Timestamp, filter_begin: Dto, filter_end: Dto): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper.location.get_all_history", timestamp).asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get_history(session: SqlSession, id: Long, timestamp: Timestamp): Option[Dto_location] = {
    return Some( session.selectOne("lars.model.mybatis.mapper.location.get_history", new HashMap[Long, Timestamp](1).put(id, timestamp)).asInstanceOf[Dto_location] )
  }


  def _save(session: SqlSession, record: Dto) {
    session.insert("lars.model.mybatis.mapper.location.insert", record)
  }


  def _overwrite(session: SqlSession, record: Dto) {
    session.update("lars.model.mybatis.mapper.location.update", record)
  }


  def _delete(session: SqlSession, record: Dtos) {
    session.delete("lars.model.mybatis.mapper.location.delete", record.asJava)
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
