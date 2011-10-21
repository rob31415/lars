package lards.model.service

import org.apache.ibatis.session.SqlSession
import collection.JavaConversions._
import java.util._
import lards.global.Applocal
import lards.model.dto.{Role => Dto_role}
import lards.model.dto.Dto
import lards.model.dto.Dtos
import lards.model.event.{Role => Event}
import java.sql.Timestamp



class Role extends Dao {

  def _get_all(session: SqlSession, timestamp: Timestamp): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper.Role.get_all", timestamp).asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get(session: SqlSession, id: Long, timestamp: Timestamp): Option[Dto_role] = {
    return Some( session.selectOne("lars.model.mybatis.mapper.Role.get", new HashMap[Long, Timestamp](1).put(id, timestamp)).asInstanceOf[Dto_role] )
  }


  def _get_all_history(session: SqlSession, timestamp: Timestamp, filter_begin: Dto, filter_end: Dto): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper.Role.get_all_history", timestamp).asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get_history(session: SqlSession, id: Long, timestamp: Timestamp): Option[Dto_role] = {
    return Some( session.selectOne("lars.model.mybatis.mapper.Role.get_history", new HashMap[Long, Timestamp](1).put(id, timestamp)).asInstanceOf[Dto_role] )
  }


  def _save(session: SqlSession, record: Dto) {
    session.insert("lars.model.mybatis.mapper.Role.insert", record)
  }


  def _overwrite(session: SqlSession, record: Dto) {
    session.update("lars.model.mybatis.mapper.Role.update", record)
  }


  def _delete(session: SqlSession, record: Dtos) {
    session.delete("lars.model.mybatis.mapper.Role.delete", record.asJava)
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
