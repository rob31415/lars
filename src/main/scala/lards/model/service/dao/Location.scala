package lards.model.service

import org.apache.ibatis.session.SqlSession
import collection.JavaConversions._
import java.util._
import lards.global.Applocal
import lards.model.dto.{Location => Dto_Location}
import lards.model.dto.Dto
import lards.model.dto.Dtos
import lards.model.event.{Location => Event}



class Location extends Dao {

  def _get_all(session: SqlSession): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper.Location.get_all").asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get(session: SqlSession, id: Long): Option[Dto_Location] = {
    return Some( session.selectOne("lars.model.mybatis.mapper.Location.get_by_id", id).asInstanceOf[Dto_Location] )
  }


  def _save_new(session: SqlSession, record: Dto) {
    session.insert("lars.model.mybatis.mapper.Location.insert", record)
  }


  def _save_existing(session: SqlSession, record: Dto) {
    session.update("lars.model.mybatis.mapper.Location.update", record)
  }


  def _delete(session: SqlSession, record: Dtos) {
    session.delete("lars.model.mybatis.mapper.Location.delete", record.asJava)
  }


  def on_success_delete() {
    Applocal.broadcaster.publish(new Event('deleted))
  }


  def on_success_insert() {
    Applocal.broadcaster.publish(new Event('inserted))
  }


  def on_success_update() {
    Applocal.broadcaster.publish(new Event('updated))
  }

}
