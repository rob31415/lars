package lars.model.service

import org.apache.ibatis.session.SqlSession
import collection.JavaConversions._
import java.util._
import lars.global.Applocal
import lars.model.dto.{_REPLACE_ => Dto__REPLACE_}
import lars.model.dto.Dto
import lars.model.dto.Dtos
import lars.model.event.{_REPLACE_ => Event}



class _REPLACE_ extends Dao {

  def _get_all(session: SqlSession): Dtos = {
    val data = session.selectList("lars.model.mybatis.mapper._REPLACE_.get_all").asInstanceOf[java.util.ArrayList[Dto]]
    return new Dtos(Some( new HashSet[Dto](data) ))
  }


  def _get(session: SqlSession, id: Long): Option[Dto__REPLACE_] = {
    return Some( session.selectOne("lars.model.mybatis.mapper._REPLACE_.get_by_id", id).asInstanceOf[Dto__REPLACE_] )
  }


  def _save_new(session: SqlSession, record: Dto) {
    session.insert("lars.model.mybatis.mapper._REPLACE_.insert", record)
    //@TODO: remove next line if no n:n is wanted
    session.delete("lars.model.mybatis.mapper._REPLACE_._REPLACE_", record) //insert in jointable
  }


  def _save_existing(session: SqlSession, record: Dto) {
    session.update("lars.model.mybatis.mapper._REPLACE_.update", record)
    //@TODO: remove next two lines if no n:n is wanted
    session.delete("lars.model.mybatis.mapper._REPLACE_._REPLACE_", record) //delete in jointable
    session.delete("lars.model.mybatis.mapper._REPLACE_._REPLACE_", record) //insert in jointable
  }


  def _delete(session: SqlSession, record: Dtos) {
    session.delete("lars.model.mybatis.mapper._REPLACE_.delete", record.asJava)
    session.delete("lars.model.mybatis.mapper._REPLACE_._REPLACE_", record)  //delete in jointable
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
